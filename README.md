# spring-shopping
### 서버 구성
- 레이어드 아키텍쳐 구성
  - Controller
  - Service
  - Repository
- 단, 멀티모듈 구성까지는 진행하지 않는다.
- 샘플 형태로 hello world 출력하는 1 cycle 구성
```
package 로 모듈 구분
ㄴ web : controller, request, response
ㄴ application : service
ㄴ core : entity, repository 
```

### 상품(product) 도메인 구성
- 속성 : 이름, 가격, 이미지 경로
- 도메인 애플리케이션 로직 구성
  - 조회, 추가, 수정, 삭제
- 별도 데이터베이스 없이 적절한 코틀린 컬렉션 프레임워크를 이용해 메모리 저장


### API 추가
- 필요한 API : 상품 조회, 추가, 수정, 삭제
- HTTP 요청과 응답은 JSON 형식으로 주고받는다.
- 예시
```
Request
> GET /api/products HTTP/1.1

Response
> HTTP/1.1 200 
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

### 유효성 검사 및 예외처리
- spring-boot-starter-validation 을 이용한다.
- 상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있다.
- 특수 문자
  - 가능: ( ), [ ], +, -, &, /, _
  - 그 외 특수 문자 사용 불가
- 상품 이름에는 비속어를 포함할 수 없다.
  - PurgoMalum 을 이용한다 (외부 API 호출) 
