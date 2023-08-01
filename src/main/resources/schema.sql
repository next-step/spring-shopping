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

CREATE TABLE cart
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity   INT    NOT NULL,
    UNIQUE (member_id, product_id),
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);
