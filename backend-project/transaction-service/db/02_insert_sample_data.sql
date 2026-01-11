INSERT INTO transactions (transaction_id, amount, status, customer_id, created_at)
VALUES
('TXN001', 500.00, 'SUCCESS', 'CUST001', now()),
('TXN002', 1200.00, 'FAILED', 'CUST002', now()),
('TXN003', 2500.00, 'PENDING', 'CUST003', now());
