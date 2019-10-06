package com.cyio.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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
//		List<Game> ret = new ArrayList<Game>();
//
//		for (int i = 0; i < limit; i ++) {
//			Game g = new Game(title, UUID.randomUUID(), UUID.randomUUID());
//			ret.add(g);
//		}
		return gameRepository.findAll();
		//return ret;
	}

	@PostMapping("/addgame")
	public @ResponseBody String addNewUser(@RequestParam String title, @RequestParam String creatorid){ //adds a new row in the games table
		UUID newID = UUID.randomUUID(); //generate a random UUID for the new Game
		Game game = new Game(title,newID.toString(),creatorid);
		gameRepository.save(game); //Insert new game to the database
		return "Game \""+ title +"\" Added";
	}
}
