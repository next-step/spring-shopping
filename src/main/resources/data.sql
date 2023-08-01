-- product table
INSERT INTO product(name, image_url, price) VALUES('치킨', 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/3904681644/B.jpg?452000000', 18000);
INSERT INTO product(name, image_url, price) VALUES('피자', 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/3904681644/B.jpg?452000000', 18000);
INSERT INTO product(name, image_url, price) VALUES('마라샹궈', 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/3904681644/B.jpg?452000000', 18000);
INSERT INTO product(name, image_url, price) VALUES('햄버거', 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/3904681644/B.jpg?452000000', 18000);
INSERT INTO product(name, image_url, price) VALUES('만두', 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/3904681644/B.jpg?452000000', 18000);
INSERT INTO product(name, image_url, price) VALUES('제육볶음', 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/3904681644/B.jpg?452000000', 18000);
INSERT INTO product(name, image_url, price) VALUES('서브웨이', 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/3904681644/B.jpg?452000000', 18000);
INSERT INTO product(name, image_url, price) VALUES('돈까스', 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/3904681644/B.jpg?452000000', 18000);

-- member table
INSERT INTO member(email, password) VALUES('woowa1@woowa.com', '1234');
INSERT INTO member(email, password) VALUES('woowa2@woowa.com', '1234');

-- cart table
INSERT INTO cart(member_id, product_id, quantity) VALUES (1L, 1L, 5);
INSERT INTO cart(member_id, product_id, quantity) VALUES (1L, 2L, 5);
INSERT INTO cart(member_id, product_id, quantity) VALUES (1L, 3L, 5);