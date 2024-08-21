USE expenses;

CREATE TABLE expenses (
                          id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          description VARCHAR(1000),
                          amount BIGINT UNSIGNED NOT NULL,
                          currency VARCHAR(3) NOT NULL DEFAULT 'PLN',
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
