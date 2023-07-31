# spring-shopping

## Step2 사용자 / 로그인

이 미션은 쇼핑 서비스의 사용자와 관련된 기능을 구현하는 미션입니다.
사용자가 로그인을 통해 사용자별 기능을 사용할 수 있게 만듭니다.

### 요구사항

- [ ] 사용자 도메인 모델 설계
- [ ] 로그인 기능 구현
- [ ] 로그인 페이지 연동

### 사용자 도메인 모델 설계

- 사용자가 가지고 있는 정보는 다음과 같습니다.
- 필요한 경우 사용자 정보의 종류를 추가할 수 있습니다.(ex. 닉네임, 주소)

``` 
## 사용자 기본 정보
- email
- password
```

### 로그인 기능 구현

- 사용자별 장바구니 기능을 위해 사용자 인증을 구현합니다.
- 인증은 토큰 방식으로 이뤄지며 JWT 토큰을 활용합니다.

```
Request
POST /login/token HTTP/1.1
content-type: application/json
host: localhost:8080

{
    "password": "password",
    "email": "admin@email.com"
}
```

```
Response
HTTP/1.1 200
Content-Type: application/json

{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjcyNjUyMzAwLCJleHAiOjE2NzI2NTU5MDAsInJvbGVzIjpbIlJPTEVfQURNSU4iLCJST0xFX0FETUlOIl19.uaUXk5GkqB6QE_qlZisk3RZ3fL74zDADqbJl6LoLkSc"
}
```

### 로그인 페이지 연동

- login.html 파일을 이용하여 사용자 로그인 기능을 완성합니다.
- `/login` url로 접근할 경우 로그인 페이지를 조회할 수 있어야 합니다.

## Step1 상품

이 미션은 Spring Web MVC를 이용하여 쇼핑 서비스의 상품 관련 기능을 구현하는 미션입니다.
사용자는 서비스가 제공하는 상품 목록을 확인할 수 있습니다.
이를 위해 상품 도메인을 설계하고, 상품 목록 API를 구현 후 페이지와 연동합니다.

### 요구사항

- [x] 상품 목록 기능 구현
- [x] 상품 목록 페이지 연동

#### 상품 목록 기능 구현

- 상품이 가지고 있는 정보는 다음과 같습니다.
- 필요한 경우 상품 정보의 종류를 추가할 수 있습니다.(ex. 상품 설명, 상품 카테고리)

#### 상품 기본 정보

- 상품 ID
- 상품 이름
- 상품 이미지
- 상품 가격
- 상품 목록 API는 주어진 형식이 없으며 분석한 도메인에 맞춰 자유롭게 설계합니다.

#### 상품 목록 페이지 연동

- index.html 파일을 이용하여 상품 목록이 노출되는 페이지를 완성합니다.
- `/` url로 접근할 경우 상품 목록 페이지를 조회할 수 있어야 합니다.


