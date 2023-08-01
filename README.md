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
POST /cart/products/{productid}
```
- [ ] 장바구니에 상품 아이템 추가

#### Request
```
GET /carts
```
- [ ] 장바구니에 담긴 아이템 목록 조회

#### Request
```
PATCH /cart/products/{productid}

{
  quantity: 1
}
```
- [ ] 장바구니에 담긴 아이템 수량 변경
  - [ ] 수량이 0이 되면 제거 

#### Request
```
DELETE /cart/products/{productid}
```
- [ ] 장바구니에 담긴 아이템 제거

- [ ] 장바구니 페이지 연동

### 장바구니 검증
- 장바구니 상품 개수는 1이상이어야 합니다.