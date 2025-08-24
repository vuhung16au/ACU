package com.acu.redis.demo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public Receiver testReceiver() {
        return new Receiver();
    }

    @Bean
    @Primary
    public MessageListenerAdapter testListenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    @Primary
    public RedisMessageListenerContainer testContainer(RedisConnectionFactory connectionFactory,
                                                      MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new org.springframework.data.redis.listener.PatternTopic("chat"));
        return container;
    }

    @Bean
    @Primary
    public StringRedisTemplate testTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}
