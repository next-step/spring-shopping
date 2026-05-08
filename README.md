# spring-shopping

# 기능 요구 사항 

## API 설계 
- [x] 상품을 1개 조회한다. GET /api/products/{id}
- [x] 상품을 여러개 조회한다.  GET /api/products
- [x] 상품을 1개 등록한다. POST /api/products
- [x] 유효한 상품 id 기준으로 상품을 1개 수정한다. PUT /api/proucts/{id}
- [x] 수정할 때 상품 이름은 공백 포함 15자를 초과하면 에러가 발생한다
- [x] 유효하지 않은 id로 수정할 때 IllegalArgumentException 예외를 발생한다
- [x] 유효한 상품 id 기준으로 상품을 1개 삭제한다. DELETE /api/products/{id}
- [x] 유효하지 않은 id로 삭제할 때 IllegalArgumentException 예외를 발생한다
- [x] API 는 Restful 하게 설계한다. 
- [x] API 요청과 응답은 JSON 형식으로 주고 받는다.

## 상품 도메인
- [x] 상품에는 이름과 가격, 이미지가 있다.
- [x] 상품 등록 시 정수형 ID 를 채번하여 관리한다. 
- [x] 상품 ID 는 유니크한 값으로 채번한다.
- [x] 상품 이미지는 URL을 입력 받는다.
- [x] 상품 이미지는 URL이 아니면 에러를 발생한다
- [x] 현재는 별도의 데이터베이스가 없으므로 적절한 코틀린 컬렉션 프레임워크를 사용하여 메모리에 저장한다.
  - [x] ProductRepository 에서 Map<Long, Product> 타입으로 메모리에 올려서 관리한다.

## 유효성 검사 및 예외 처리
- [ ] 상품을 추가하거나 수정하는 경우, 클라이언트로부터 잘못된 값이 전달될 수 있다. 잘못된 값이 전달되면 클라이언트가 어떤 부분이 왜 잘못되었는지 인지할 수 있도록 응답을 제공한다.
- [x] 상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있다.
- [x] 상품 이름은 공백을 포함하여 15자 초과하면 에러가 발생한다
- [ ] 상품 이름은 다음의 특수 문자 가능하다.: ( ), [ ], +, -, &, /, _  (그 외 특수 문자 사용 불가)
- [ ] 상품 이름에는 비속어를 포함할 수 없다. 비속어는 PurgoMalum에서 욕설이 포함되어 있는지 확인한다.
- [ ] 아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.

```
Request
GET /api/products HTTP/1.1
Response
HTTP/1.1 200
Content-Type: application/json

[
    {
        "id": 8146027,
        "name": "아이스 카페 아메리카노 T",
        "price": 4500,
        "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
    }
]
```