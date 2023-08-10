DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

CREATE TABLE products
(
    id    bigint auto_increment primary key,
    name  varchar(20)  not null,
    image varchar(255) not null,
    price bigint       not null
);

CREATE TABLE users
(
    id       bigint auto_increment primary key,
    email    varchar(50)  not null,
    password varchar(255) not null
);

CREATE TABLE cart_items
(
    id         bigint auto_increment primary key,
    user_id    bigint not null,
    product_id bigint not null,
    quantity   int    not null,
    foreign key (user_id) references users (id) on delete cascade
);

CREATE TABLE orders
(
    id          bigint auto_increment primary key,
    user_id     bigint not null,
    total_price bigint not null,
    foreign key (user_id) references users (id) on delete cascade
);

CREATE TABLE order_items
(
    id         bigint auto_increment primary key,
    order_id   bigint       not null,
    product_id bigint       not null,
    name       varchar(20)  not null,
    image      varchar(255) not null,
    price      bigint       not null,
    quantity   int          not null,
    foreign key (order_id) references orders (id) on delete cascade
);

ALTER TABLE order_items
    ADD unique (order_id, product_id);
