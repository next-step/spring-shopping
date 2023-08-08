# spring-shopping

## Domain

### Product
- ID
- name
- imageUrl
- price

### Member
- ID
- email
- password

### CartProduct
- ID
- Member
- Product
- quantity

### Order
- ID
- Member
- orderedAt

### OrderItem
- ID
- Order
- name
- price
- quantity
- imageUrl

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

#### Request
```
POST /cart/products/{productId}

Authorization: Bearer {jwt_token}
```
- [x] 장바구니에 상품 추가
  - [x] 이미 상품이 장바구니에 존재하면 수량을 1 증가한다

#### Request
```
GET /cart/products

Authorization: Bearer {jwt_token}
```
- [x] 장바구니에 담긴 상품 목록 조회

#### Request
```
PATCH /cart/{id}

Authorization: Bearer {jwt_token}

{
  quantity: 1
}
```
- [x] 장바구니에 담긴 상품 수량 변경
  - [x] 수량이 0개일 경우, 상품을 제거한다

#### Request
```
DELETE /cart/{id}

Authorization: Bearer {jwt_token}
```
- [x] 장바구니에 담긴 상품 제거

### 장바구니 검증
- 장바구니 상품 개수는 1이상이어야 합니다.

### 장바구니에 담긴 상품 전부 주문

#### Request
```
POST /order

Authorization: Bearer {jwt_token}
```

#### Response
```
Location: /order-history/{orderId}
```
- [ ] 주문에 성공하면 Created 를 반환한다.

### 주문 상세 정보 조회

#### Request
```
GET /order-history/{orderId}

Authorization: Bearer {jwt_token}
```

#### Response
```
{
  orderId: 1
  orderItems: [{
    name: test,
    price: 10,
    imageUrl: aaa,
    quantity: 100
  }]
  totalPrice: 1000
}

```
- [ ] 주문 id 와 일치하는 주문 정보를 반환한다.
- [ ] 주문자 정보와 요청을 보낸 사용자 정보가 일치하지 않으면 Bad Request 를 반환한다.
- [ ] 주문 id 와 일치하는 주문 정보가 존재하지 않으면 Bad Request 를 반환한다.

### 사용자 주문 목록 조회

#### Request
```
POST /order-history

Authorization: Bearer {jwt_token}
```

#### Response
```
- 주문 아이템 정보
```

- [ ] 사용자 주문 목록 조회에 성공하면 OK 를 반환한다.