package com.virality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.virality.entity.Post;
import com.virality.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private RedisTemplate<String , Object> redisTemplate;
	
	public Post createPost(Post post) {
		return postRepository.save(post);
	}
	
	public String likePost(Long postId) {
		
		 String key = "post:" + postId + ":virality_score";
		    redisTemplate.opsForValue().increment(key, 20);
		    return "Post " + postId + " liked!";
		
	}
	
	
}
