package com.virality.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.virality.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
