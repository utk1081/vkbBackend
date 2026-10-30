package com.example.insuranceproposal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.example.insuranceproposal.domain.InsuranceApplication;

@Configuration
public class RedisConfig {

	@Bean
	 public RedisTemplate<String, InsuranceApplication> redisTemplate(
	            RedisConnectionFactory connectionFactory) {

	        RedisTemplate<String, InsuranceApplication> template = new RedisTemplate<>();

	        template.setConnectionFactory(connectionFactory);

	        template.setKeySerializer(new StringRedisSerializer());
	        template.setValueSerializer(new JacksonJsonRedisSerializer<>(InsuranceApplication.class));
// using yaml config Spring boot tc of Redis Infra like  ConnectFactory object creation, instan, manage . this is classic Auto config
	        return template;
	    }
}
