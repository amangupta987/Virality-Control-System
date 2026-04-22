package com.virality.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.virality.entity.Bot;

public interface BotRepository extends JpaRepository<Bot, Long> {

}
