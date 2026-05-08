# spring-shopping

## 기능 요구 사항

온라인 쇼핑몰을 위한 간단한 HTTP API를 구현한다.
HTTP 요청과 응답은 JSON 형식으로 주고받으며 스프링 프레임워크를 사용하여 웹 앱으로 구현한다.

---

## 상품

상품을 조회, 추가, 수정, 삭제할 수 있는 간단한 기능을 구현한다.

- 상품에는 이름, 가격, 이미지가 있다.
- 상품 이미지는 파일을 업로드하지 않고 URL을 직접 입력한다.
- 별도의 데이터베이스 없이 코틀린 컬렉션을 사용하여 메모리에 저장한다.

---

## 유효성 검사 및 예외 처리

상품을 추가하거나 수정할 때 잘못된 값이 전달되면,
클라이언트가 어떤 부분이 왜 잘못되었는지 인지할 수 있도록 응답을 제공한다.

### 상품 이름

- 공백 포함 최대 15자
- 허용 특수 문자: `( )`, `[ ]`, `+`, `-`, `&`, `/`, `_`
- 그 외 특수 문자 사용 불가
- 비속어 포함 불가 ([PurgoMalum](https://www.purgomalum.com) 을 통해 검증)

---

## API 명세

### 상품 목록 조회

**Request**
```
GET /api/products HTTP/1.1
```

**Response**
```json
HTTP/1.1 200
Content-Type: application/json

[
  {
    "id": 1,
    "name": "아이스 카페 아메리카노 T",
    "price": 4500,
    "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
  }
]
```

### 상품 추가

**Request**
```json
POST /api/products HTTP/1.1
Content-Type: application/json

{
  "name": "아이스 카페 아메리카노 T",
  "price": 4500,
  "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
}
```

**Response**
```
HTTP/1.1 201 Created
Location: /api/products/1
```

---

## 구현 체크리스트

### 상품명 검증
- [x] 공백 포함 최대 15자 제한
- [x] 허용된 특수 문자만 사용 가능
- [x] PurgoMalum API 연동 비속어 검증

### 상품 API
- [x] 상품 조회
- [x] 상품 추가
- [x] 상품 수정
- [ ] 상품 삭제
- [ ] 이미지 URL 형식 검증
