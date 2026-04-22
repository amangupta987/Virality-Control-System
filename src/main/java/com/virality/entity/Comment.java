package com.virality.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long postId;
	
	private Long authorId;
	
	private String content;
	
	private int depthLevel;
	
	private LocalDateTime createdAt;
	
	@PrePersist
	public void setCreatedAt() {
	    this.createdAt = LocalDateTime.now();
	}
}
