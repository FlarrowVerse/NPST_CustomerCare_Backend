CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    transaction_id VARCHAR(50),
    amount NUMERIC(12,2),
    status VARCHAR(20),
    customer_id VARCHAR(50),
    merchant VARCHAR(100),
    payment_method VARCHAR(50),
    created_at TIMESTAMP,
    refunded BOOLEAN DEFAULT FALSE,
    refunded_amount NUMERIC(12, 2) DEFAULT 0.0,
    parent_transaction_id VARCHAR(50)
);
