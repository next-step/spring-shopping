insert into products(image, name, price)
values ('/assets/img/chicken.png', '치킨', 20000),
       ('/assets/img/hamburger.jpeg', '햄버거', 10000),
       ('/assets/img/pizza.jpg', '피자', 20000);

insert into members(email, password)
values ('woowacamp@naver.com', 'woowacamp');

insert into cart_products(member_id, product_id, quantity)
values (1, 1, 3),
       (1, 2, 1);

insert into orders(member_id, status)
values (1, 'ORDERED'),
       (1, 'ORDERED');

insert into order_products(order_id, ordered_image, ordered_name, ordered_price, quantity)
values (1, '/assets/img/chicken.png', '치킨', 20000, 3),
       (1, '/assets/img/hamburger.jpeg', '햄버거', 10000, 1),
       (1, '/assets/img/pizza.jpg', '피자', 20000, 2),
       (2, '/assets/img/hamburger.jpeg', '햄버거', 10000, 1),
       (2, '/assets/img/chicken.png', '치킨', 20000, 1)
