CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255)
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    amount NUMERIC(19,4) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    description VARCHAR(255),
    status VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    order_reference VARCHAR(255) NOT NULL UNIQUE,

    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE payment (
    id BIGSERIAL PRIMARY KEY,
    amount NUMERIC(19,4) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    order_id BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    payment_reference VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_payment_order
        FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE transaction (
    id BIGSERIAL PRIMARY KEY,
    amount NUMERIC(19,4) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    status VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    payment_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    description VARCHAR(255),
    transaction_reference VARCHAR(255) NOT NULL UNIQUE,

    CONSTRAINT fk_transaction_user
        FOREIGN KEY (user_id) REFERENCES users(id),

    CONSTRAINT fk_transaction_payment
        FOREIGN KEY (payment_id) REFERENCES payment(id)
);