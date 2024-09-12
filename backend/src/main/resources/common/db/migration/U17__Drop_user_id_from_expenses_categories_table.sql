USE expenses;

ALTER TABLE expenses_categories
    DROP FOREIGN KEY fk_user_expense_category;

ALTER TABLE expenses_categories
    DROP COLUMN user_id;
