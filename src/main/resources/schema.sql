CREATE TABLE product
(
    id    bigint auto_increment primary key,
    name  varchar(20)  not null,
    image varchar(255) not null,
    price int          not null
);

CREATE TABLE user
(
    id       bigint auto_increment primary key,
    email    varchar(50)  not null,
    password varchar(255) not null
);
