package com.virality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.virality.entity.Comment;
import com.virality.exception.CooldownException;
import com.virality.exception.TooManyRequestsException;
import com.virality.repository.BotRepository;
import com.virality.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	@Autowired
	private BotRepository botRepository;
	@Autowired
	private NotificationService notificationService;
	
	public Comment addComment(Comment comment) {

	    Long authorId = comment.getAuthorId();
	    Long postId = comment.getPostId();
	    boolean isBot = botRepository.existsById(authorId);

	    if(comment.getDepthLevel() > 20) {
	        throw new RuntimeException("Max depth exceeded");
	    }

	    if(isBot) {
	    	String cooldownKey = "cooldown:bot_" + authorId + ":human_" + postId;
	        Boolean exists = redisTemplate.hasKey(cooldownKey);

	        if(Boolean.TRUE.equals(exists)) {
	            throw new CooldownException("Cooldown active - try after 15 min");
	        }

	        String botKey = "post:" + postId + ":bot_count";
	        Long count = redisTemplate.opsForValue().increment(botKey);

	        if(count != null && count > 100) {
	            redisTemplate.opsForValue().decrement(botKey);
	            throw new TooManyRequestsException("Bot limit exceeded");
	        }

	        redisTemplate.opsForValue().set(cooldownKey,"1",java.time.Duration.ofMinutes(15));
	    }

	    
	    String key = "post:" + postId + ":virality_score";

	    if(isBot) {
	        redisTemplate.opsForValue().increment(key, 1);
	        Long userId = postId;

	        notificationService.handleNotification(userId,"Bot interacted with your post");
	    } else {
	        redisTemplate.opsForValue().increment(key, 50);
	    }

	   
	    return commentRepository.save(comment);
	}
}
