package com.cyio.backend;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
	
	private static final String template = "Title: %s";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/restexample")
	public Game game(@RequestParam(value = "title", defaultValue = "cy.io") String title){
		return new Game(counter.incrementAndGet(),
					String.format(template,title));
	}

}
