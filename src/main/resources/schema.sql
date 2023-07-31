CREATE TABLE products
(
    id    bigint auto_increment primary key,
    name  varchar(20)  not null,
    image varchar(255) not null,
    price int          not null
);

CREATE TABLE users
(
    id       bigint auto_increment primary key,
    email    varchar(50)  not null,
    password varchar(255) not null
);
