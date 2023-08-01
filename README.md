# spring-shopping

## Step 1. 상품

- [x] 상품은 `상품 ID`, `상품 이름`, `상품 이미지`, `상품 가격`으로 이루어져 있다.
- [x] 상품 목록 페이지 연동

## Step 2. 사용자 / 로그인

- [x] 사용자 정보는 `email`, `password`로 이루어져 있다.
- [x] 로그인 인증 방식은 JWT로 합니다.
	- request
		```
		POST /login/token
		{
			"password": "password",
			"email": "admin@email.com"
		}
		```
	- response
		```
		{
			"accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjcyNjUyMzAwLCJleHAiOjE2NzI2NTU5MDAsInJvbGVzIjpbIlJPTEVfQURNSU4iLCJST0xFX0FETUlOIl19.uaUXk5GkqB6QE_qlZisk3RZ3fL74zDADqbJl6LoLkSc"
		}
		```
- [x] 로그인 페이지 연동
	- `/login` url로 접근할 경우 로그인 페이지 조회
	- 로그인 이후 상품목록 페이지로 redirect
	- 로그인 이후 페이지 헤더 처리(로그인 링크 삭제)는 하지 않아도 됨
- [x] 이메일 혹은 비밀번호가 올바르지 않으면 `400 Bad Request`와 오류 메시지 반환
- [x] 이메일이 `null`인 경우 오류 메시지 반환
- [x] 비밀번호가 `null`인 경우 오류 메시지 반환

## Step 3. 장바구니

- `User`와 `Product` 사이 N:M 관계 테이블
- 사용자 정보는 요청 Header의 `Authrozation` 필드를 이용
- 인증 방식은 bearer
- `Authrozation: <type> <credentials>`

- [x] 장바구니에 상품 아이템 추가
	- request
		```
		POST /carts
		{
			"productId": 1
		}
		```
	- response `201 CREATED`
	- [x] `productId`가 `null`이면 오류 메시지 반환
	- [x] `productId`가 0 또는 음수이면 오류 메시지 반환
	- [x] `productId`가 존재하지 않으면 오류 메시지 반환

- [x] 장바구니에 담긴 아이템 목록 조회
	- request : `GET /carts`

- [x] 장바구니에 담긴 아이템 수량 변경
	- request
		```shell
		PATCH /carts/{id}
		{
		"quantity": 3
		}
		```
	- response : `204 NO CONTENT`
	- [x] `quantity`가 0 또는 음수이면 오류 메시지 반환
	- [ ] `cartItemId`가 db에 존재하지 않는 경우 오류 메시지 반환

- [x] 장바구니에 담긴 아이템 제거
	- request : `DELETE /carts/{id}`
	- response : `204 NO CONTENT`
	- [ ] `cartItemId`가 db에 존재하지 않는 경우 오류 메시지 반환

- [x] 장바구니 페이지 연동
- [ ] 사용자 토큰이 만료되었을 시 인증 실패 메시지 반환
- [ ] 사용자 토큰이 `null`인 경우 인증 실패 메시지 반환
- [ ] 사용자 토큰이 올바르지 않은 경우 인증 실패 메시지 반환

## 커밋 규칙

커밋 메시지는 아래 형식을 따르도록 함

```shell
<commit_type>: <commit_message>
```

commit type은 아래 목록 중 하나로 함

- `feat`
- `test`
- `refactor`
- `docs`
- `fix`
- `chore`