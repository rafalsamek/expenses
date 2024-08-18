ALTER TABLE expenses
    DROP FOREIGN KEY fk_wallet_id;

ALTER TABLE expenses
    DROP COLUMN wallet_id;
