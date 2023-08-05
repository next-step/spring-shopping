CREATE TABLE IF NOT EXISTS `product`
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    name      VARCHAR(255)          NOT NULL,
    image_url VARCHAR(255)          NOT NULL,
    price     BIGINT                NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `users`
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    email    VARCHAR(100)          NOT NULL UNIQUE,
    password VARCHAR(100)          NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `cart_item`
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_id    BIGINT                NOT NULL,
    product_id BIGINT                NOT NULL,
    quantity   INT                   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE IF NOT EXISTS `order`
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    user_id     BIGINT                NOT NULL,
    total_price BIGINT                NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS `order_item`
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    order_id            BIGINT                NOT NULL,
    original_product_id BIGINT                NOT NULL,
    name                VARCHAR(255)          NOT NULL,
    image_url           VARCHAR(255)          NOT NULL,
    price               BIGINT                NOT NULL,
    quantity            INT                   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES order (id),
    FOREIGN KEY (original_product_id) REFERENCES product (id)
);
