drop table if exists PRODUCT;
drop table if exists MEMBER;

create table if not exists PRODUCT
(
    product_id bigint auto_increment not null,
    image varchar(255) not null,
    name varchar(30) not null,
    price int not null,
    primary key(product_id)
);

create table if not exists MEMBER
(
    member_id bigint auto_increment not null,
    nickname varchar(10) not null unique,
    email varchar(40) not null unique,
    password varchar(18) not null,
    primary key(member_id)
);
