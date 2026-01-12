INSERT INTO transactions (
    transaction_id, 
    amount, 
    status, 
    customer_id, 
    merchant, 
    payment_method, 
    created_at, 
    refunded, 
    refunded_amount, 
    parent_transaction_id
)
VALUES
('TXN001', 500.00, 'SUCCESS', 'CUST001', 'Amazon', 'UPI', now(), FALSE, 0.0, ''),
('TXN002', 1200.00, 'FAILED', 'CUST002', 'PVR Inox', 'Credit Card', now(), FALSE, 0.0, ''),
('TXN003', 2500.00, 'PENDING', 'CUST003', 'Myntra', 'Debit Card', now(), FALSE, 0.0, '');
