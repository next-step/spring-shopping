CREATE TABLE IF NOT EXISTS product
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(15)  NOT NULL,
    price     INT          NOT NULL,
    image_url VARCHAR(255) NOT NULL
);