# spring-shopping

## Domain

### Product
- product_id
- name
- imageUrl
- price

### Member
- member_id
- email
- password

### CartProduct
- cart_product_id
- Member
- Product
- quantity

### Order
- order_id
- Member

### OrderProduct
- order_product_id
- Order
- Product
- quantity

## ✅ 기능 정리

### 상품 검증
- [x] 상품 이름은 최대 25자
- [x] 가격은 0원 이상
- [x] 상품 이름은 중복 불가

### 사용자 검증
- [x] 이메일 형식 검증

### 상품 조회

- [x] 상품목록 API
- [x] 상품목록 html 완성

### 로그인
#### Request
```
POST /login
{
    "password": "password",
    "email": "admin@email.com"
}
```
#### Response
payload : Member(ID)
```
{
    "accessToken": {jwt_token}
}
```
- [x] 로그인 API
- [x] 사용자 검증
- [x] 토큰 생성 및 반환

### 장바구니
#### Request Header
```
Authorization: Bearer {jwt_token}
```

#### Request
```
POST /cart/products/{productId}
```
- [x] 장바구니에 상품 추가
  - [x] 이미 상품이 장바구니에 존재하면 수량을 1 증가한다

#### Request
```
GET /cart/products
```
- [x] 장바구니에 담긴 상품 목록 조회

#### Request
```
PATCH /cart/{id}

{
  quantity: 1
}
```
- [x] 장바구니에 담긴 상품 수량 변경
  - [x] 수량이 0개일 경우, 상품을 제거한다

#### Request
```
DELETE /cart/{id}
```
- [x] 장바구니에 담긴 상품 제거

### 장바구니 검증
- 장바구니 상품 개수는 1이상이어야 합니다.

## 주문
#### Request Header
```
Authorization: Bearer {jwt_token}
```
#### Request
```
POST /orders
```
#### Response Header
```
Location: /orders/{orderId}
```
- [x] 장바구니에 담긴 아이템 전체 주문
  - [x] 주문 요청이 성공하면 주문 상세 페이지로 이동

#### Request
```
GET /orders/{orderId}
```
#### Response
```
{
    orderId : {orderId},
    products : [
        {
            name = {name},
            imageUrl = {imageUrl},
            price = {price},
            quantity = {quantity}
        },
        ...
    ],
    totalPrice : {totalPrice}
}
```
- [x] 주문 상세 조회
  - 주문 정보 : 주문 번호, 주문 아이템 정보 (이름, 가격, 이미지, 수량), 총 결제 금액

#### Request
```
GET /orders
```
#### Response
```
[
    {
        orderId : {orderId},
        products : [
            {
                name = {name},
                imageUrl = {imageUrl},
                price = {price},
                quantity = {quantity}
            },
            ...
        ],
    },
    ...
]
```
- [x] 주문 목록 조회
  - 주문 정보 : 주문 번호, 주문 아이템 정보 (이름, 가격, 이미지, 수량)