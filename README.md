# spring-shopping

## Step4 주문

이번 미션에서는 장바구니에 담긴 아이템을 주문하고 이전 주문을 확인하는 기능을 구현합니다.
사용자가 장바구니 목록 페이지에서 주문하기 버튼을 누르면 바로 주문이 완료되는 시나리오 입니다.
상품과 장바구니와 주문 도메인의 관계를 고려하여 설계해 주세요.

### 요구사항

### 주문 기능 구현

- [x] 쇼핑 주문과 관련된 아래 기능을 구현합니다.
    - [x] 장바구니에 담긴 아이템 전체 주문
    - [x] 특정 주문의 상세 정보를 확인
    - [x] 사용자 별 주문 목록 확인
- [x] 장바구니 기능과 동일하게 사용자 정보는 요청 Header의 Authorization 필드를 사용해 인증 처리를 하여 얻습니다.
- [x] 주문 정보에는 아래의 내용이 담겨 있어야 합니다.

#### 주문 기본 정보

- 주문 번호
- 주문 아이템 정보
    - 이름
    - 가격
    - 이미지
    - 수량
- 총 결제금액

### 주문 페이지 연동

- [x] 장바구니에 담긴 아이템 전체 주문
    - [x] 장바구니 목록 페이지(/cart)에서 주문하기 버튼을 통해 장바구니에 담은 아이템을 주문할 수 있습니다.
    - [x] 주문 요청이 성공하면 주문 상세 페이지로 이동합니다.
    - [x] Authorization 헤더에 Bearer 토큰을 통해 사용자를 파악합니다.
    - [x] 사용자의 장바구니 속의 아이템을 구매합니다.
    ```
    POST /orders HTTP/1.1
    content-type: application/json
    host: localhost:8080  
    ```
- [x] 주문 상세 정보
    - [x] `/order-history/{orderId}` url로 접근할 경우 주문 상세 페이지를 조회할 수 있어야 합니다.
    - [x] order-detail.html 파일을 이용하여 특정 주문의 상세 정보를 확인할 수 있게 만듭니다.
    - [x] 페이지에서 주문 id를 알 수 있도록 페이지를 내려주는 Controller에서 orderId를 attribute로 추가해야 빠르게 연동할 수 있습니다.
    - [x] Authorization 헤더에 Bearer 토큰을 통해 사용자를 파악합니다.
    - [x] 사용자의 주문이 아닐시 403 Forbidden을 반환합니다.
    - Page
    ``` 
    GET /order-history/{orderId} HTTP/1.1
    host: localhost:8080
    ```
    - Order
    ```
    GET /orders/{orderId} HTTP/1.1
    host: localhost:8080
    ```
- [x] 사용자 별 주문 목록 확인
    - [x] `/order-history` url로 접근할 경우 주문 목록 페이지를 조회할 수 있어야 합니다.
    - [x] order-history.html 파일을 이용하여 사용자 별 주문 목록을 확인할 수 있게 만듭니다.
    - [x] 상세보기 버튼을 클릭해 주문 상세 정보 페이지로 이동할 수 있습니다.
    - [x] Authorization 헤더에 Bearer 토큰을 통해 사용자를 파악합니다.
    - Page
    ```
    GET /order-history HTTP/1.1
    host: localhost:8080
    ```
    - Orders
    ```
    GET /orders HTTP/1.1
    host: localhost:8080
    ```

## Step3 장바구니

이번 미션에서는 사용자별로 장바구니에 상품을 담고, 장바구니에 담긴 아이템을 확인하고 변경하는 기능을 만듭니다.
2단계 미션에서 로그인 후 받은 accessToken을 사용하여 사용자별 장바구니 기능을 구현합니다.
문서 최하단에 있는 사용자 시나리오를 참고하여, 장바구니 아이템과 관련된 비즈니스 로직에 맞게 동작할 수 있도록 합니다.

### 요구사항

- [x] 장바구니 기능 구현
- [x] 장바구니 페이지 연동

#### 장바구니 기능 구현

- 장바구니와 관련된 아래 기능을 구현합니다.
    - [x] 장바구니에 상품 아이템 추가
        ```
        POST /cart/items HTTP/1.1
        content-type: application/json
        host: localhost:8080
        
        {
            "productId": 1
        }
        ```
    - [x] 장바구니에 담긴 아이템 목록 조회
        ```
        GET /cart/items HTTP/1.1
        host: localhost:8080
        ```
    - [x] 장바구니에 담긴 아이템 수량 변경
        ```
        PATCH /cart/items/1 HTTP/1.1
        content-type: application/json
        host: localhost:8080
        
        {
            "quantity": 2
        }
        ```
    - [x] 장바구니에 담긴 아이템 제거
        ```
        DELETE /cart/items/1 HTTP/1.1
        content-type: application/json
        host: localhost:8080
        ```
- 사용자 정보는 요청 Header의 Authorization 필드를 사용해 인증 처리를 하여 얻습니다.
    - [x] 인증 방식은 bearer인증을 사용합니다.
    - [x] `Authorization: <type> <credentials>`
        - type: Bearer
        - credentials : jwt token
- 예시)
    ```
    Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
    ```
- 그 외 API 스펙은 정해진것이 없으므로 API 설계를 직접 진행 한 후 기능을 구현 합니다.
- 기존에 구현되어있는 상품을 바탕으로 장바구니 기능을 위한 객체와 테이블 그리고 패키지 구조는 자유롭게 설계할 수 있습니다.

#### 장바구니 페이지 연동

- 장바구니 아이템 추가
    - [x] 상품 목록 페이지(/)에서 담기 버튼을 통해 상품을 장바구니에 추가할 수 있습니다.
- 장바구니 아이템 목록 조회 및 제거
    - [x] cart.html 파일과 장바구니 관련 API를 이용하여 장바구니 페이지를 완성합니다.
    - [x] `/cart` url로 접근할 경우 장바구니 페이지를 조회할 수 있어야 합니다.
    - [x] 장바구니의 아이템의 수량을 변경하거나 삭제하는 기능을 동작하게 만듭니다.

## Step2 사용자 / 로그인

이 미션은 쇼핑 서비스의 사용자와 관련된 기능을 구현하는 미션입니다.
사용자가 로그인을 통해 사용자별 기능을 사용할 수 있게 만듭니다.

### 요구사항

- [x] 사용자 도메인 모델 설계
- [x] 로그인 기능 구현
- [x] 로그인 페이지 연동

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


