package com.cyio.backend.model;

import org.springframework.security.core.parameters.P;
import org.springframework.web.socket.sockjs.client.SockJsClient;

import java.util.UUID;

public class Player {

    private String id;
    private String name;
    private SockJsClient socket;

    public Player(SockJsClient socket) {
        this(UUID.randomUUID().toString());
        this.socket = socket;
    }

    public Player(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SockJsClient getSocket() {
        return socket;
    }

}
