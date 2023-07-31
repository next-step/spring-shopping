CREATE TABLE product
(
    id    bigint auto_increment primary key,
    name  varchar(20)  not null,
    image varchar(255) not null,
    price int          not null
);
