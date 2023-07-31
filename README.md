# spring-shopping

## Domain

### Product
- ID
- 이름
- 이미지
- 가격

### Member
- ID
- email
- password

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
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjcyNjUyMzAwLCJleHAiOjE2NzI2NTU5MDAsInJvbGVzIjpbIlJPTEVfQURNSU4iLCJST0xFX0FETUlOIl19.uaUXk5GkqB6QE_qlZisk3RZ3fL74zDADqbJl6LoLkSc"
}
```
- [ ] 로그인 API
- [ ] 사용자 검증
- [ ] 토큰 생성 및 반환
- [ ] 로그인 후, 상품목록 페이지(/)로 리다이렉트