package com.cyio.backend.repository;

import com.cyio.backend.model.Game;
import com.cyio.backend.model.GameServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServerRepository extends JpaRepository<GameServer, String> {
    List<GameServer> findGameServerByGameId(String gameId);
    GameServer findGameServerByServerId(String serverId);
}
