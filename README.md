# Virality Control System
A high-performance Spring Boot microservice designed to simulate a real-world social media backend with strict guardrails, concurrency control, and intelligent notification batching using Redis.

# Objective
The goal of this system is to safely handle high-volume interactions (likes, comments, bot replies) while preventing uncontrolled compute usage using Redis-based atomic operations and rate-limiting strategies.

# Tech Stack
1. Java 17
2. Spring Boot 3
3. MySQL
4. Redis (Spring Data Redis)
5. Docker

# Core Features

 1. Real-Time Virality Engine

* Each post has a **Virality Score** stored in Redis
* Score updates instantly on interactions:

  1. 👍 Human Like → +20
  2. 💬 Human Comment → +50
  3. 🤖 Bot Reply → +1


2. Redis-Based Atomic Guardrails (Thread-Safe)

To prevent system abuse and ensure concurrency safety, Redis atomic operations were used:

# Horizontal Cap (Bot Limit)

1. Redis Key: `post:{id}:bot_count`
2. Used `INCR` operation
3. Ensures max **100 bot replies per post**
4. If exceeded → request rejected with **HTTP 429**

# Vertical Cap (Thread Depth)

* Max comment depth allowed: **20**
* Prevents infinite nested threads

# Cooldown Cap (Rate Limiting)

* Redis Key: `cooldown:bot_{id}:human_{id}`
* Implemented using `SET with TTL`
* Prevents bot from interacting with same user within **15 minutes**


# How Thread Safety is Guaranteed

Redis ensures thread safety through **atomic operations**:

1. `INCR` → Atomic increment ensures no race condition even under concurrent requests
2. `SET with TTL` → Atomic key creation + expiration
3.  No in-memory variables (no HashMap/static usage)

# Example Scenario:

> 200 concurrent bot requests hit the same post simultaneously

* Redis `INCR` guarantees:

  * Counter increases safely without overlap
  * Only first 100 succeed
  * Remaining are rejected

# Notification Engine (Smart Batching)

To avoid user notification spam:

# First Interaction

* Notification sent instantly
* Redis key set: `notif:cooldown:user_{id}` (15 min TTL)

# Subsequent Interactions

* Stored in Redis List:
* `user:{id}:pending_notifs`

# Scheduled Processing

* Runs every **5 minutes**
* Reads pending notifications
* Sends summarized message:


Summarized Push Notification:
User X has N new interactions 


# API Endpoints

| Method | Endpoint                   | Description        |
| ------ | -------------------------- | ------------------ |
| POST   | `/api/posts`               | Create post        |
| POST   | `/api/posts/{id}/like`     | Like post          |
| POST   | `/api/posts/{id}/comments` | Add comment        |
| GET    | `/api/posts/{id}/score`    | Get virality score |



# Testing & Edge Cases

✔ Handled 200 concurrent requests using Redis atomic counters
✔ Ensured strict bot limit (max 100)
✔ Implemented cooldown-based rate limiting
✔ Maintained stateless architecture
✔ Redis used as gatekeeper before DB commit

# Docker Support

The project includes a `docker-compose.yml` file to run:

* Redis
* MySQL

# Key Highlights

* Designed a **stateless backend architecture**
* Used Redis as a **distributed control layer**
* Prevented race conditions using **atomic operations**
* Built a scalable notification system using **batch processing**


# Conclusion

This project demonstrates real-world backend engineering concepts including:

* Concurrency handling
* Rate limiting
* Distributed state management
* Event-driven scheduling



=>>>> This is not just a CRUD project — it is a **system design-oriented backend implementation**


## How to Run

1. Start Redis and MySQL (or use Docker)
2. Run Spring Boot application
3. Import Postman collection
4. Test APIs