package com.virality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	public void handleNotification(Long userId, String message) {
		String cooldownKey ="notif:cooldown:user_" + userId;
		
		Boolean exists = redisTemplate.hasKey(cooldownKey);
		if(Boolean.TRUE.equals(exists)) {
			String listKey =  "user:" + userId + ":pending_notifs";
			redisTemplate.opsForList().rightPush(listKey, message);
		}else {
			System.out.println("Push notification sent to user " + userId);
			
			redisTemplate.opsForValue().set(cooldownKey, "1" , java.time.Duration.ofMinutes(15));
		}
	}
	
	@Scheduled(fixedRate = 300000)
	public void processNotifications() {
		 var keys = redisTemplate.keys("user:*:pending_notifs");
		 
		 if(keys == null || keys.isEmpty()) return;
		  for(String key : keys) {

		        Long size = redisTemplate.opsForList().size(key);
		        if(size == null || size == 0) continue;
		        String userId = key.split(":")[1];

		        System.out.println("Push Notification: User " +
		            userId + " has " + size + " new interactions "
		        );

		        redisTemplate.delete(key);
		    }
	}
}
