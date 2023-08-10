# 쇼핑

## 진행 방법

* 쇼핑 요구 사항을 파악한다.
* 요구 사항에 대한 구현을 완료한 후 자신의 Github 아이디에 해당하는 브랜치에 Pull request를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 Push한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 페어 프로그래밍

### 목표

* 박종하: 쫓기지 않고 기본적인 것에 충실하자
* 최정규: 여유를 갖고 웃으면서 하자

### 규칙

* 기간을 두고 구현을 완벽하지 않더라도 내기
* 잡담이 너무 길어지면 짜르기 (입코딩 금지)
* 설계에 시간을 많이 쓰기

## 용어 사전

| 한글명     | 영문          |
|---------|-------------|
| 상품      | Product     |
| 회원      | Member      |
| 장바구니 상품 | CartProduct |

## 요구 사항

### 1단계

- [x] 상품 도메인을 정의한다.
    - [x] 상품은 ID, 이름, 이미지, 가격으로 구성된다.
- [x] 원시값을 포장한다.
    - [x] 상품 이름을 포장한다.
    - [x] 상품 URL 포장한다.
    - [x] 상품 가격을 포장한다.
- [x] 웹 페이지와 연동한다.
    - [x] 상품 목록을 보여준다.

### 2단계

- [x] 회원 도메인을 정의한다.
    - [x] 회원은 ID, 이메일, 비밀번호로 구성된다.
- [x] 원시값을 포장한다.
    - [x] 회원 이메일을 포장한다.
        - [x] 이메일은 공백이면 안된다.
        - [x] 이메일 정규식을 만족해야한다.
    - [x] 회원 비밀번호를 포장한다.
        - [x] 비밀번호는 공백이면 안된다.
        - [x] 비밀번호는 30자 이하여야한다.
- [ ] 로그인 기능
    - [x] 이메일과 비밀번호를 입력하면 로그인을 할 수 있다.
    - [x] 인증은 JWT를 이용한다.
        - [x] claim은 id 정보를 갖는다.
        - [x] expire time은 1시간이다.
        - [x] 인증에 성공하면 accessToken을 반환한다.
        - [x] 암호화 알고리즘은 HS256을 사용한다.
    - [ ] (optional) refresh token 기능을 지원한다.
    - [x] 이메일이 존재하지 않으면 `존재하지 않는 이메일입니다.`, Bad Request로 응답한다.
    - [x] 이메일 양식에 맞지 않으면 `회원 이메일이 형식에 맞지 않습니다.`, Bad Request로 응답한다.
    - [x] 비밀번호가 일치하지 않으면 `비밀번호가 일치하지 않습니다.`, Bad Request로 응답한다.
- [x] 웹 페이지와 연동한다.
    - [x] 로그인 페이지로 이동할 수 있다.
    - [x] 로그인에 성공하면 메인 페이지로 리다이렉트된다.

### 3단계

- [x] 장바구니 도메인을 정의한다.
    - [x] 장바구니는 장바구니를 소유한 회원 ID, 장바구니 물품들로 구성된다.
    - [x] 장바구니는 비어있을 수 없다.
- [x] 장바구니 상품 도메인을 정의한다.
    - [x] 장바구니 상품은 회원 ID, 상품 ID, 상품 개수로 구성된다.
    - [x] 장바구니 상품 개수는 1개 이상이여야한다.
- [x] 장바구니와 관련된 아래 기능을 구현합니다.
    - [x] 장바구니 기능은 인증된 회원만 사용할 수 있다.
        - [x] 인가는 JWT 토큰을 이용하며 요청 헤더의 `Authorization` 값에 `Bearer ${accessToken}`형식이어야한다.
        - [x] `Authorization` 헤더가 없는 경우 `토큰 헤더가 존재하지 않습니다.`, Unauthorized로 응답한다.
        - [x] 토큰 값이 없는 경우 `토큰 값이 존재하지 않습니다.`, Unauthorized로 응답한다.
        - [x] 토큰 값이 양식에 맞지 않는 경우 `토큰이 Bearer로 시작하지 않습니다.`, Unauthorized로 응답한다.

    - [x] 장바구니에 상품 아이템 추가
        - [x] 장바구니에 상품을 추가할 수 있다.
        - [x] 상품이 존재하지 않는 경우 `존재하지 않는 상품입니다.`로 응답한다.
        - [x] 장바구니에 이미 상품이 있는 경우 `이미 장바구니에 담긴 상품입니다.`로 응답한다.
    ```http request
    POST /api/cartProduct
    
    {
      "productId": 5
    }
    ```
    - [x] 장바구니에 담긴 아이템 목록 조회
    ```http request
    GET /api/cartProduct
    ``` 

    - [x] 장바구니에 담긴 아이템 수량 변경
    - [x] 장바구니 상품 개수가 0개인 경우, OK로 장바구니 상품이 제거된다.
    - [x] 장바구니 담긴 상품이 존재하지 않는 경우 `존재하지 않는 장바구니 상품입니다.`, Bad Request로 응답한다.
    - [x] 장바구니 상품 개수를 0개 이하로 변경하는 경우 `0개 이하로 수량을 변경할 수 없습니다.`, Bad Request로 응답한다.
    ```http request
    PATCH /api/cartProduct/{cartProductId}
    
    {
      "quantity": 5
    }
    ```

    - [x] 장바구니에 담긴 아이템 제거
    ```http request
    DELETE /api/cartProduct/{cartProductId}
    ```

- [x] 웹 페이지와 연동한다.
    - [x] 로그인 페이지로 이동할 수 있다.
    - [x] 로그인에 성공하면 메인 페이지로 리다이렉트된다.

### 4단계

- [x] 도메인을 정의한다.
    - [x] 주문 도메인
        - [x] 주문한 회원, 주문 시간, 주문 상태(주문, 주문 취소), 주문 상품 목록을 갖는다.
    - [x] 주문 상품 도메인
        - [x] 주문, 주문 당시 상품 이미지, 주문 당시 상품 이름, 주문 당시 상품 가격, 주문 개수
- [ ] 주문 요구 사항을 구현한다.
    - [x] 회원은 장바구니에 담긴 상품을 주문할 수 있다.
        - [x] end-point
            - request
                ```http request
                POST /api/order
                ``` 
            - response
                ```http request
                {
                  "orderId": 1
                }
                ```
        - [x] 장바구니의 모든 상품만 주문할 수 있다. (개별 구매 X)
        - [x] 주문에 성공할 경우 장바구니의 상품을 모두 삭제한다.
    - [ ] 회원은 주문 내역들을 조회할 수 있다.
        - [ ] 본인의 주문 내역만 확인할 수 있다.
            - [x] end-point
                - request
                    ```http request
                    GET /api/order
                    ``` 
                - response
                    ```http request
                    [
                        {
                          "orderId": 1,
                          "orderProducts": [
                            {
                            "orderedImage": "/assets/img/hamburger.jpeg",
                            "orderedName": "햄버거",
                            "orderedPrice": 10000,
                            "quantity": 3
                            }
                          ]
                        },
                        {
                          "orderId": 2,
                          "orderProducts": [
                            {
                            "orderedImage": "/assets/img/hamburger.jpeg",
                            "orderedName": "햄버거",
                            "orderedPrice": 10000,
                            "quantity": 3
                            }
                          ]
                        }
                    ]
                   ```
    - [x] 회원은 주문 목록 중 개별의 주문 상세 내역을 확인할 수 있다.
        - [x] 본인의 주문 목록만 확인할 수 있다.
        - [x] endpoint
            * request
              ```http request
              GET /api/order/{orderId}
              ```
            * response
              ```http request
              {
                "orderId": 1,
                "orderProducts": [
                  {
                    "orderedImage": "/assets/img/chicken.png",
                    "orderedName": "치킨",
                    "orderedPrice": 20000,
                    "quantity": 3
                  },
                  {
                    "orderedImage": "/assets/img/hamburger.jpeg",
                    "orderedName": "햄버거",
                    "orderedPrice": 10000,
                    "quantity": 1
                  },
                  {
                    "orderedImage": "/assets/img/pizza.jpg",
                    "orderedName": "피자",
                    "orderedPrice": 20000,
                    "quantity": 2
                  }
                ],
                "totalPrice": 50000
              }
              ```
- [x] 웹 페이지와 연동한다.
    - [x] 주문 목록 페이지로 이동할 수 있다.
        - [x] 각각의 주문에 대하여 상세 정보를 확인할 수 있다.

### 5단계

- [ ] 환율 적용 요구 사항을 구현한다.
    - [ ] 실시간 환율을 가져올 수 있어야한다.
    - [ ] 외부 API의 의존성을 최대한 줄인다.
        - [ ] API를 다른 API로 변경하였을 때 코드의 변경이 최소한으로 이루어져야한다.
        - [ ] 외부 API의 문제로 환율 정보를 가져오지 못했을 때도 원화 주문은 원활하게 작동해야한다.
    - [ ] 환율 정보는 3초 안에 조회할 수 있어야한다.
- [ ] 웹 페이지와 연동한다.
    - [ ] 주문 상세 정보 페이지에서 환율 정보를 출력한다.
        - [x] 총 주문 금액을 출력한다.
        - [ ] 적용 환율을 출력한다.
        - [ ] 환율이 적용된 총 주문 금액을 출력한다.
