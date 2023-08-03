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

### Cart
- ID
- Member
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
  - [x] 수량은 1개 이상이어야한다

#### Request
```
DELETE /cart/{id}
```
- [x] 장바구니에 담긴 상품 제거

### 장바구니 검증
- 장바구니 상품 개수는 1이상이어야 합니다.