# spring-shopping

온라인 쇼핑몰을 위한 간단한 HTTP API 구현 프로젝트

## 기능 요구사항
- HTTP 요청과 응답은 JSON 형식으로 주고받으며 스프링 프레임워크를 사용하여 웹 앱으로 구현


### 상품 관리
- [x] 상품 목록 조회 
- [x] 상품 단건 조회
- [x] 상품을 추가할 수 있다 
- [x] 상품을 추가하면 유니크한 ID 를 채번한다.
- [x] 상품을 수정할 수 있다
- [x] 상품을 삭제할 수 있다
- [x] 상품 정보: 이름, 가격, 이미지 URL

### 유효성 검사
- [x] 상품 이름은 공백 포함 최대 15자까지 입력할 수 있다
- [x] 상품 이름에 허용되는 특수 문자: `( ) [ ] + - & / _`
- [x] 상품 이름에 비속어를 포함할 수 없다 
- [x] 비속어는 PurgoMalum API로 검증한다. (FakeProfanities 로 대체 한다)
- [x] 상품 이미지는 URL 형식으로 입력한다
- [x] 잘못된 값이 전달되면 어떤 부분이 왜 잘못되었는지 응답으로 전달한다

## API

### 상품 목록 조회
```
GET /api/products

HTTP/1.1 200 OK
Content-Type: application/json

[
  {
    "id": 1,
    "name": "아이스 카페 아메리카노 T",
    "price": 4500,
    "imageUrl": "https://example.com/image.jpg"
  }
]
```

### 상품 추가
```
POST /api/products

HTTP/1.1 201 Created
Location: /api/products/1
```

### 상품 수정
```
PUT /api/products/{id}

HTTP/1.1 204 No Content
```

### 상품 삭제
```
DELETE /api/products/{id}

HTTP/1.1 204 No Content
```

## 기술 스택

- Kotlin + Spring Boot 3
- H2 (인메모리 DB) (메모리 -> 2단계 진행)
- Kotest + MockK (테스트)
