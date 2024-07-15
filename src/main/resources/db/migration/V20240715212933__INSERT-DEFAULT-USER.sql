INSERT INTO users
(id, full_name, email, "password", created_at, updated_at, "version", is_active, deleted_at)
VALUES('e89c33f6-eee9-4bf6-9b67-082be53e371c', 'User 1', 'user1@live.com', '$2a$10$3fYMkJi1.j3dzzYGCsVyaue8xuazDAcBu5BelQXI0WzJdO15xnArm', now(), null, 0, true, null)
ON CONFLICT DO NOTHING;