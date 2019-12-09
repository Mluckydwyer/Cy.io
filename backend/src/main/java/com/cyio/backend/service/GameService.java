package com.cyio.backend.service;

import com.cyio.backend.model.Game;
import com.cyio.backend.repository.GameRepository;
import com.cyio.backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.cyio.backend.service.QueryType.TITLE;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository; //using autowire to create an instance of game Repository

    @Autowired
    public SimpMessagingTemplate template;

    /**
     * @return a list of all games on the server
     */
    public List<Game> listAllGames(){
        return gameRepository.findAll();
    }

    /**
     * finds games based on query type and query
     * @param queryType
     * @param query
     * @return the games matching the query
     */
    public List<Game> findGame(QueryType queryType, String query){
        switch (queryType) {
            case TITLE:
                return gameRepository.findGameByTitleContaining(query);
            case BLURB:
                return gameRepository.findGameByBlurbContaining(query);
            case ABOUT:
                return gameRepository.findGameByAboutContaining(query);
            default:
                return gameRepository.findGameByTitleContainingOrBlurbContainingOrAboutContainingOrderByTitle(query, query, query);
        }
    }

    /**
     *  add a new game to the server
     */
    public String addGame(String title, String creatorid) throws IOException{
        //check if a game with the same title already exists
        if (!findGame(TITLE, title).isEmpty())
            throw new IOException("game with same title already exits");

        UUID newID = UUID.randomUUID(); //generate a random UUID for the new Game
        Game game = new Game(title,newID.toString(),creatorid);
        gameRepository.save(game); //Insert new game to the database
        template.convertAndSend("/topic/notifications", "New Game " + game.getTitle() +" Added!");
        return "Game \""+ title +"\" Added";
    }

    public String deleteGames(String titleOrId, UserPrincipal principal){
        List<Game> resultList = gameRepository.findGameByTitleContaining(titleOrId);
        Game game = null;
        if (!resultList.isEmpty())
            game = resultList.get(0);
        if (game != null && (principal.isAdmin() || game.getCreatorID().equals(principal.getId()))){
            gameRepository.deleteGameByTitle(titleOrId);
            return "Success";
        }
        return "Game does not exist or not authorized";
    }
}
