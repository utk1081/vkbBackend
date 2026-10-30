package com.example.insuranceproposal.repository;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.insuranceproposal.domain.InsuranceApplication;

@Repository
public class RedisApplicationRepository implements ApplicationRepository {

	
	private final RedisTemplate<String, InsuranceApplication> redisTemplate;
	
	public  RedisApplicationRepository(RedisTemplate<String, InsuranceApplication> template) {
		 this.redisTemplate=template;
		 System.out.println("RedisApplicationRepository created");
	}
	
	@Override
	public void save(InsuranceApplication application) {
		// TODO Auto-generated method stub
		String key="application:" +application.getApplicationId();
		 System.out.println("Saving application with key: " + key);
		redisTemplate.opsForValue().set(key, application, Duration.ofHours(24));
		//opsForValue().set(key, application) stores the application as a Redis value. Your configured serializers handle converting the key and value into Redis-compatible representations.
	}

	@Override
	public Optional<InsuranceApplication> findById(UUID uuid) {
		String key="application:"+uuid;
		InsuranceApplication insuApp=redisTemplate.opsForValue().get(key);//Notice that get() expects the Redis key, which is a String, not a UUID.
		if(insuApp==null) {
			return Optional.empty();
		}
		return Optional.of(insuApp);
	}

}
