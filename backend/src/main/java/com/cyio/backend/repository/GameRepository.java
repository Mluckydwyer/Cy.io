package com.cyio.backend.repository;

import com.cyio.backend.model.Game;
import com.cyio.backend.model.GameServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    //List<Game> findByTitleContainingOrContentContaining(String title, String about);
    List<Game> findGameByTitleContainingOrBlurbContainingOrAboutContainingOrderByTitle(String Title, String Blurb, String About);
    List<Game> findGameByTitleContaining(String Title);
    List<Game> findGameByBlurbContaining(String Blurb);
    List<Game> findGameByAboutContaining(String About);
}
