package com.virality.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virality.entity.Post;
import com.virality.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@PostMapping
	public Post createPost(@RequestBody Post post) {
		return postService.createPost(post);
	}
	
	@PostMapping("/{postId}/like")
	public String likePost(@PathVariable Long postId) {
		
		return postService.likePost(postId);
	}
	@GetMapping("/{postId}/score")
	public Object getScore(@PathVariable Long postId) {
		String key = "post:" + postId + ":virality_score";
		return redisTemplate.opsForValue().get(key);
	}
}
