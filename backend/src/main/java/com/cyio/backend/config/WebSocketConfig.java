package com.cyio.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/game/", "/chat/");
//        config.setApplicationDestinationPrefixes("/app");
        //config.enableSimpleBroker("/secured/user/queue/specific-user", "/secured");
        config.enableSimpleBroker("/room");
        config.setApplicationDestinationPrefixes("/app");
        //config.setUserDestinationPrefix("/secured/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/game").withSockJS();
//        registry.addEndpoint("/chat")
//                .setHandshakeHandler(new DefaultHandshakeHandler() {
//
//            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map attributes) throws Exception {
//                if (request instanceof ServletServerHttpRequest) {
//                    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//                    HttpSession session = servletRequest.getServletRequest().getSession();
//                    attributes.put("sessionId", session.getId());
//                }
//                return true;
//            }}).withSockJS();
        registry.addEndpoint("/secured/room").setAllowedOrigins("http://localhost:63343").withSockJS();
        //registry.addEndpoint("/secured/chat").setAllowedOrigins("http://localhost:63343").withSockJS();
        //registry.addEndpoint("/secured/history").setAllowedOrigins("http://localhost:63343").withSockJS();
        }

}
