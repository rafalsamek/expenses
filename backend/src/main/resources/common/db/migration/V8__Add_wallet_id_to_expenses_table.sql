ALTER TABLE expenses
ADD COLUMN wallet_id BIGINT NOT NULL DEFAULT 1 AFTER currency,
ADD CONSTRAINT fk_wallet_id FOREIGN KEY (wallet_id) REFERENCES wallets(id);
