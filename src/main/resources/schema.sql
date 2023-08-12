CREATE TABLE product
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(25)  NOT NULL UNIQUE,
    image_url VARCHAR(255) NOT NULL,
    price     BIGINT       NOT NULL
);

CREATE TABLE member
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE cart_product
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity   INT    NOT NULL,
    UNIQUE (member_id, product_id),
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);

CREATE TABLE orders
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id     BIGINT    NOT NULL,
    created_at    TIMESTAMP NOT NULL,
    exchange_rate DECIMAL   NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id)
);

CREATE TABLE orders_item
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id   BIGINT       NOT NULL,
    name       VARCHAR(25)  NOT NULL,
    image_url  VARCHAR(255) NOT NULL,
    price      BIGINT       NOT NULL,
    quantity   INT          NOT NULL,
    product_id BIGINT       NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id)
);