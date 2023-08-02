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

create table if not exists CART_ITEM
(
    cart_item_id bigint auto_increment not null,
    member_id bigint not null,
    product_id bigint not null,
    quantity int not null,
    primary key(cart_item_id),
    foreign key (member_id) references MEMBER(member_id),
    foreign key (product_id) references PRODUCT(product_id)
);
