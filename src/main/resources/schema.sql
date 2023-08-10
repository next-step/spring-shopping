drop table if exists order_item;
drop table if exists orders;
drop table if exists cart_item;
drop table if exists product;
drop table if exists member;

create table if not exists product (
                                       id bigint generated by default as identity,
                                       file_name varchar(255),
    image_store_type varchar(255),
    name varchar(255),
    price numeric(19,2),
    primary key (id)
    );

create table if not exists member (
                                      id bigint generated by default as identity,
                                      email varchar(255),
    password varchar(255),
    primary key (id)
    );

create table cart_item (
                           id bigint generated by default as identity,
                           member_id bigint,
                           product_id bigint,
                           product_name varchar(255),
                           product_price numeric(19,2),
                           image_url varchar(255),
                           quantity integer,
                           primary key (id),
                           FOREIGN KEY (member_id) REFERENCES member(id),
                           FOREIGN KEY (product_id) REFERENCES product(id)
);

create table orders (
                        id bigint generated by default as identity,
                        member_id bigint,
                        total_price numeric(19,2),
                        primary key (id),
                        FOREIGN KEY (member_id) REFERENCES member(id)
);

create table order_item (
                            order_id bigint not null,
                            name varchar(255),
                            price numeric(19,2),
                            product_id bigint,
                            quantity integer,
                            image_url varchar(255),
                            FOREIGN KEY (order_id) REFERENCES orders(id)
);
