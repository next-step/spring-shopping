CREATE TABLE product
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(25)  NOT NULL UNIQUE,
    image_url VARCHAR(255) NOT NULL,
    price     BIGINT       NOT NULL
);

CREATE TABLE member
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(255)  NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL
);
