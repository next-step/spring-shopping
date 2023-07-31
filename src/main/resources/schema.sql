create table if not exists PRODUCT
(
    product_id bigint auto_increment not null,
    image varchar(255) not null,
    name varchar(30) not null,
    price int not null,
    primary key(product_id)
);
