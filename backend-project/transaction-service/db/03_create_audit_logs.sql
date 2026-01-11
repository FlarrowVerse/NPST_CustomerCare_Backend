CREATE TABLE IF NOT EXISTS audit_logs (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50),
    role VARCHAR(50),
    action VARCHAR(100),
    endpoint VARCHAR(100),
    timestamp TIMESTAMP NOT NULL DEFAULT now()
);
