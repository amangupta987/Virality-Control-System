package com.virality.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virality.entity.Comment;
import com.virality.service.CommentService;

@RestController
@RequestMapping("/api/posts")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/{postId}/comments")
	public Comment addComment(@PathVariable Long postId ,
			@RequestBody Comment comment) {
		comment.setPostId(postId);
		return commentService.addComment(comment);
	}
}
