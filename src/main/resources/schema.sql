CREATE TABLE product
(
    product_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(25)  NOT NULL UNIQUE,
    image_url VARCHAR(255) NOT NULL,
    price     BIGINT       NOT NULL
);

CREATE TABLE member
(
    member_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE cart_product
(
    cart_product_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity   INT    NOT NULL,
    UNIQUE (member_id, product_id),
    FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE
);

CREATE TABLE orders
(
    order_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (member_id)
);

CREATE TABLE order_product
(
    order_product_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity   INT    NOT NULL,
    UNIQUE (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE
);
