USE expenses;

CREATE TABLE wallets (
                          id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description VARCHAR(1000),
                          currency VARCHAR(3) NOT NULL DEFAULT 'PLN',
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
