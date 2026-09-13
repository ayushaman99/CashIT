CREATE TABLE payment_link (
    id BIGSERIAL PRIMARY KEY,
    link_reference VARCHAR(255) NOT NULL UNIQUE,
    order_id BIGINT,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_payment_link_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id)
);