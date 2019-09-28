package com.cyio.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
	
	private static final String template = "Title: %s";
	private final AtomicLong counter = new AtomicLong();
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/restexample")
	public Game game(@RequestParam(value = "title", defaultValue = "cy.io") String title){
		return new Game(counter.incrementAndGet(),
					String.format(template,title));
	}

    @CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/gamelist")
	public List<Game> gameList(@RequestParam(value="searchtitle", defaultValue = "*") String title){
		Game g1 = new Game(counter.incrementAndGet(), String.format(template,title));
		Game g2 = new Game(counter.incrementAndGet(), "Space Invader");
		List<Game> ret = new ArrayList<Game>();
		ret.add(g1);
		ret.add(g2);
		return ret;
	}


}
