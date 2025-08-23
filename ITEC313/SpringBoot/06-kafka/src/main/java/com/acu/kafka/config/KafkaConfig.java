package com.acu.kafka.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
public class KafkaConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

    @Bean
    public DefaultErrorHandler kafkaErrorHandler() {
        // Configure error handler to skip problematic records
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
            (consumerRecord, exception) -> {
                logger.warn("Skipping problematic record: topic={}, partition={}, offset={}, error={}",
                    consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset(), exception.getMessage());
            },
            new FixedBackOff(1000L, 2L) // Retry 2 times with 1 second delay
        );
        
        // Configure which exceptions to skip
        errorHandler.addNotRetryableExceptions(
            org.apache.kafka.common.errors.RecordDeserializationException.class,
            org.springframework.kafka.support.serializer.DeserializationException.class
        );
        
        return errorHandler;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(kafkaErrorHandler());
        return factory;
    }
}
