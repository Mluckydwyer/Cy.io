package com.cyio.backend.controller;

import java.io.IOException;
import java.util.List;

import com.cyio.backend.model.Game;
import com.cyio.backend.security.CurrentUser;
import com.cyio.backend.security.UserPrincipal;
import com.cyio.backend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cyio.backend.service.QueryType;

import static com.cyio.backend.service.QueryType.*;


@RestController
public class GameController {

	@Autowired
	private GameService gameService;


//  @CrossOrigin(origins = "http://localhost:3000")
//	@RequestMapping("/restexample")
//	public Game game(@RequestParam(value = "title", defaultValue = "cy.io") String title){
//		return new Game("Tommy.io", "3fe2ff3a", "Tommy, the mediocre at best");
//	}

    @CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/gamelist")
	public List<Game> gameList(){
		return gameService.listAllGames();
	}

	@RequestMapping("/searchby")
	public List<Game> searchBy(@RequestParam(value="searchtype", defaultValue = "all") String searchType, @RequestParam(value="query", defaultValue = "*") String query){
		QueryType type = TITLE;
    	switch (searchType){
			 case "title": type = TITLE; break;
			case "blurb": type = BLURB; break;
			case "about": type = ABOUT; break;
			default: type = ALL; break;
		 }

		 return gameService.findGame(type, query);
	}

	/**
	 * This endpoint allows all users to add a new game to the server
	 * @param title
	 * @param creatorid
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/addgame")
	public @ResponseBody String addGame(@RequestParam String title, @RequestParam String creatorid) throws IOException { //adds a new row in the games table
		return gameService.addGame(title, creatorid);
	}

	/**
	 * :8080/deletegame?game=TITLE
	 * 	- Status: active
	 * 	- Type: POST
	 * 	- Usage: Only availabe to the game's creater and admin user. This method searches for the game specificed by "TITLE" where "TITLE" is the game title and deletes it
	 * 	- Returns success message when operation was successful and error messages othersiwe
	 *
	 * @param titleOrId
	 * @param userPrincipal
	 * @return
	 */
	@PostMapping("/deletegame")
	public @ResponseBody String deleteGame(@RequestParam (value="game") String titleOrId, @CurrentUser UserPrincipal userPrincipal){
    	return gameService.deleteGames(titleOrId, userPrincipal);
	}
}
