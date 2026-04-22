package com.virality.exception;

public class CooldownException extends RuntimeException {
	
	public CooldownException(String message) {
		super(message);
	}
}
