package com.mercadolibre.mutants;

import java.io.IOException;
import java.net.ServerSocket;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;

import redis.embedded.RedisServer;

@TestConfiguration
public class TestRedisConfiguration {

	private RedisServer redisServer;

    public TestRedisConfiguration(RedisProperties redisProperties) throws IOException {
    	ServerSocket socket = new ServerSocket(0);
        int port = socket.getLocalPort();
        socket.close();
        this.redisServer = new RedisServer(port);
    }
    
    @PostConstruct
    public void postConstruct() {
    	if(!redisServer.isActive()) {
    		redisServer.start();
    	}
        
        System.out.println("Redis Embebido start");
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}
