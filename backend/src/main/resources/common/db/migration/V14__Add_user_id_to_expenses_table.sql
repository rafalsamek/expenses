USE expenses;

ALTER TABLE expenses
ADD COLUMN user_id BIGINT UNSIGNED NOT NULL DEFAULT 1 AFTER currency,
ADD CONSTRAINT fk_user_expense FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
