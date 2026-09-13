ALTER TABLE payment
ADD COLUMN razorpay_order_id VARCHAR(255);

CREATE UNIQUE INDEX uk_payment_razorpay_order_id
ON payment (razorpay_order_id);