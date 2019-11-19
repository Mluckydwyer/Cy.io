package com.cyio.backend.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.cyio.backend.model.Game;
import com.cyio.backend.repository.GameRepository;
import com.cyio.backend.security.CurrentUser;
import com.cyio.backend.security.UserPrincipal;
import com.cyio.backend.websockets.NotificationSocket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    @Autowired
	GameRepository gameRepository; //using autowire to create an instance of game Repository

	@Autowired
	NotificationSocket socket; //create an instance of
	
//  @CrossOrigin(origins = "http://localhost:3000")
//	@RequestMapping("/restexample")
//	public Game game(@RequestParam(value = "title", defaultValue = "cy.io") String title){
//		return new Game("Tommy.io", "3fe2ff3a", "Tommy, the mediocre at best");
//	}

    @CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/gamelist")
	public List<Game> gameList(@RequestParam(value="limit", defaultValue = "10") int limit, @RequestParam(value="title", defaultValue = "Cy.io Game") String title){
		return gameRepository.findAll();
	}

	@RequestMapping("/searchby")
	public List<Game> searchBy(@RequestParam(value="searchtype", defaultValue = "all") String searchType, @RequestParam(value="query", defaultValue = "*") String query){
		switch (searchType){
			case "title": return gameRepository.findGameByTitleContaining(query);
			case "blurb": return gameRepository.findGameByBlurbContaining(query);
			case "about": return gameRepository.findGameByAboutContaining(query);
			default: return gameRepository.findGameByTitleContainingOrBlurbContainingOrAboutContainingOrderByTitle(query,query,query);
		}
	}

	@PostMapping("/addgame")
	public @ResponseBody String addGame(@RequestParam String title, @RequestParam String creatorid) throws IOException { //adds a new row in the games table
		UUID newID = UUID.randomUUID(); //generate a random UUID for the new Game
		Game game = new Game(title,newID.toString(),creatorid);
		gameRepository.save(game); //Insert new game to the database
		socket.newGameAdded(game);
		return "Game \""+ title +"\" Added";
	}

	@PostMapping("/deletegame")
	public @ResponseBody String deleteGame(@RequestParam (value="game") String titleOrId, @CurrentUser UserPrincipal userPrincipal){
//    	int id = -1;
//    	if (StringUtils.isNumeric(titleOrId))
//    		id = Integer.parseInt(titleOrId);
    	List<Game> resultList = gameRepository.findGameByTitleContaining(titleOrId);
    	Game game = null;
    	if (!resultList.isEmpty())
    		 game = resultList.get(0);
    	if (game != null && (userPrincipal.isAdmin() || game.getCreatorID().equals(userPrincipal.getId()))){
    		gameRepository.deleteGameByTitle(titleOrId);
    		return "Success";
		}
    	return "Game does not exist or not authorized";
	}
}
