insert into product(image, name, price)
values ('/assets/img/chicken.png', '치킨', 20000),
       ('/assets/img/hamburger.jpeg', '햄버거', 10000),
       ('/assets/img/pizza.jpg', '피자', 20000);

insert into members(email, password)
values ('woowacamp@naver.com', 'woowacamp'),
       ('jongha@naver.com', 'jognha'),
       ('woowafriends@naver.com', 'woowafriends');

insert into cart_products(member_id, product_id, quantity)
values (1, 1, 3),
    (1, 2, 1),
    (2, 3 ,1);
insert into orders(exchange_rate, member_id, price) values (1.0, 2,60000);
insert into order_products(image, name, price, product_id, quantity, order_id)
values ('/assets/img/pizza.jpg','피자', 20000, 3L, 3, 1L);
