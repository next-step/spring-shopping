# spring-shopping

## 기능 요구사항

온라인 쇼핑몰을 위한 간단한 HTTP API를 구현한다.

HTTP 요청과 응답은 JSON 형식으로 주고받으며 스프링 프레임워크를 사용하여 웹 앱으로 구현한다.

### 상품

상품을 조회, 추가, 수정, 삭제할 수 있는 간단한 기능을 구현한다.

상품에는 이름과 가격, 이미지가 있다.

상품 이미지의 경우, 파일을 업로드하지 않고 URL을 직접 입력한다.

현재는 별도의 데이터베이스가 없으므로 적절한 코틀린 컬렉션 프레임워크를 사용하여 메모리에 저장한다.

### 유효성 검사 및 예외 처리

상품을 추가하거나 수정하는 경우, 클라이언트로부터 잘못된 값이 전달될 수 있다.
잘못된 값이 전달되면 클라이언트가 어떤 부분이 왜 잘못되었는지 인지할 수 있도록 응답을 제공한다.

상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있다.

- 특수 문자
    - 가능: ( ), [ ], +, -, &, /, _
    - 그 외 특수 문자 사용 불가

- 상품 이름에는 비속어를 포함할 수 없다.
    - PurgoMalum에서 욕설이 포함되어 있는지 확인한다.

## 기능 구현

### 도메인: 상품
- ID (M)
  - [ ] 상품 저장 순서대로 채번할 수 있다.
  - [ ] id값은 중복될 수 없다.
- 이름 (M)
    - [x] 공백 포함 최대 15자까지 입력할 수 있다.
    - [x] 특수문자는 ( ), [ ], +, -, &, /, _만 포함될 수 있다.
    - [ ] 비속어를 포함할 수 없다. 
      - PurgoMalum에서 욕설 포함 유무 확인해 본다.
      - example: https://www.purgomalum.com/service/json?text=this
- 가격 (M)
    - [x] 입력된 가격이 음수라면 에러가 발생할 수 있다.
    - [x] 가격은 자연수로 이루어져야 한다.
- 이미지 (M)

### Repository
- [ ] save
- [ ] find
- [ ] update
- [ ] delete

### Infrastructure
- HTTP PurgoMalum 연동

## 테스트 전략
- 무엇을 검증하고 싶은지?
  - [x] 도메인 규칙 -> 단위테스트
  - [x] repository가 의도대로 동작되는지 -> 단위테스트
  - 외부 통신) validator에 대한 테스트는 어디까지? 
    - [ ] restClient 모킹해서 validator.validate() 의도대로 동작하는지
  - API 요청 및 응답값 -> integration test
    - [ ] controller > request의 validate annotation 잘 동작하는지
  - [ ] service layer : stub, mock 한 객체가 호출이 의도대로 되는지
- 검증 범위가 어디까지인지?
- 의존하는 대상이 실제인지 혹은 가짜인지?
  - integration test에서는 repository 실제 객체, restClient 실제 객체

## API 명세

- 조회
    - GET /api/products 
    - Response
```
    [
      {
        "id": 8146027,
        "name": "아이스 카페 아메리카노 T",
        "price": 4500,
        "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
      }
    ]
```

- 추가
    - POST /api/products
```
    [
      {
        "name": "아이스 카페 아메리카노 T",
        "price": 4500,
        "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
      }
    ]
```
- 수정
    - PATCH /api/products/{id}
```
  [
    {
      "name": "아이스 카페 아메리카노 T",
      "price": 4500,
      "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
    }
  ]
```
- 삭제
    - DELETE /api/products/{id}
