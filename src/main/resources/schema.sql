DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

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

CREATE TABLE cart_items
(
    id         bigint auto_increment primary key,
    user_id    bigint not null,
    product_id bigint not null,
    quantity   int    not null,
    foreign key (user_id) references users (id) on delete cascade,
    foreign key (product_id) references products (id) on delete cascade
);
