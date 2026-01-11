CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    transaction_id VARCHAR(50),
    amount NUMERIC(12,2),
    status VARCHAR(20),
    customer_id VARCHAR(50),
    created_at TIMESTAMP
);
