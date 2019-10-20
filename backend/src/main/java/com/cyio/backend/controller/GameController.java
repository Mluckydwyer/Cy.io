package com.cyio.backend.controller;

import java.util.List;
import java.util.UUID;

import com.cyio.backend.model.Game;
import com.cyio.backend.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    @Autowired
	GameRepository gameRepository; //using autowire to create an instance of blog Repository
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/restexample")
	public Game game(@RequestParam(value = "title", defaultValue = "cy.io") String title){
		return new Game("Tommy.io", "3fe2ff3a", "Tommy, the best comedian in succ");

	}

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
	public @ResponseBody String addGame(@RequestParam String title, @RequestParam String creatorid){ //adds a new row in the games table
		UUID newID = UUID.randomUUID(); //generate a random UUID for the new Game
		Game game = new Game(title,newID.toString(),creatorid);
		gameRepository.save(game); //Insert new game to the database
		return "Game \""+ title +"\" Added";
	}
}
