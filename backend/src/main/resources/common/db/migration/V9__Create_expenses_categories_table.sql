USE expenses;

CREATE TABLE expenses_categories (
                                   expense_id BIGINT UNSIGNED,
                                   category_id INTEGER UNSIGNED,
                                   PRIMARY KEY (expense_id, category_id),
                                   FOREIGN KEY (expense_id) REFERENCES expenses(id) ON DELETE CASCADE,
                                   FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);
