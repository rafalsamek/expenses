USE expenses;

ALTER TABLE categories
ADD COLUMN user_id BIGINT UNSIGNED NOT NULL DEFAULT 1 AFTER description,
ADD CONSTRAINT fk_user_category FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
