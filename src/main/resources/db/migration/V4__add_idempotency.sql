CREATE TABLE idempotency_keys (
    id BIGSERIAL PRIMARY KEY,
    idempotency_key VARCHAR(255) NOT NULL UNIQUE,
    payment_id BIGINT UNIQUE,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_idempotency_payment
        FOREIGN KEY (payment_id)
        REFERENCES payment(id)
);