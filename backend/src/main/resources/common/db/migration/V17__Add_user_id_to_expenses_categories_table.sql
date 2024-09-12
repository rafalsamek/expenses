USE expenses;

ALTER TABLE expenses_categories
ADD COLUMN user_id BIGINT UNSIGNED NOT NULL DEFAULT 1 AFTER category_id,
ADD CONSTRAINT fk_user_expense_category FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
