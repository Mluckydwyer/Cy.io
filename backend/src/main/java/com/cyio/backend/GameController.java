package com.cyio.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/restexample")
	public Game game(@RequestParam(value = "title", defaultValue = "cy.io") String title){
		return new Game(title, UUID.randomUUID(), UUID.randomUUID());
	}

    @CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/gamelist")
	public List<Game> gameList(@RequestParam(value="limit", defaultValue = "10") int limit, @RequestParam(value="title", defaultValue = "Cy.io Game") String title){
		List<Game> ret = new ArrayList<Game>();

		for (int i = 0; i < limit; i ++) {
			Game g = new Game(title, UUID.randomUUID(), UUID.randomUUID());
			ret.add(g);
		}

		return ret;
	}


}
