# spring-shopping

## 프로젝트 구조

### 아키텍처 패턴
**레이어드 아키텍처** 기반으로 구성되어 있으며, 패키지별로 명확한 역할 분리가 되어 있습니다:

### 패키지 구조
```
shopping/
├── web/           # 웹 계층 (Controller, Request/Response DTO)
├── application/   # 애플리케이션 계층 (Service, Validator)
├── core/          # 도메인 계층 (Entity, Repository)
└── client/        # 외부 연동 계층 (External API Client)
```

### 주요 컴포넌트

**1. Web Layer (`shopping.web`)**
- `ProductController`: REST API 엔드포인트 제공 (CRUD)
- `ProductRequest`: 입력 검증이 포함된 요청 DTO
- `ProductResponse`: 응답 DTO

**2. Application Layer (`shopping.application`)**
- `ProductService`: 비즈니스 로직 처리
- `ProductValidator`: 상품명 비속어 검증 (PurgoMalum API 연동)

**3. Core Layer (`shopping.core`)**
- `Product`: 도메인 엔티티 (name, price, imageUrl, id)
- `ProductRepository`: 메모리 기반 데이터 저장소 (ConcurrentHashMap 사용)

**4. Client Layer (`shopping.client`)**
- `PurgomalumClient`: 외부 API 호출 담당
- `PurgomalumRestClientConfig`: RestClient Bean 설정 (@Qualifier로 구분)

### 기술 스택
- **언어**: Kotlin
- **프레임워크**: Spring Boot 3.5.9
- **검증**: Jakarta Validation (Bean Validation)
- **HTTP 클라이언트**: Spring RestClient
- **데이터 저장**: ~~메모리 기반 (AtomicLong ID 생성)~~ -> H2 데이터베이스를 사용하도록 변경
- **코드 품질**: ktlint

### 특징
1. **멀티모듈 없이 패키지로 모듈 구분**
2. **외부 API 연동을 위한 전용 Client 레이어**
3. **Bean Validation을 통한 입력 검증**
4. **~~메모리 기반 저장소로 DB 의존성 제거~~ -> H2 데이터베이스를 사용하도록 변경**
5. **@Qualifier를 통한 RestClient Bean 구분**

---
## 과제 요구사항

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
- ~~별도 데이터베이스 없이 적절한 코틀린 컬렉션 프레임워크를 이용해 메모리 저장~~
- 메모리 저장방식에서 H2 데이터베이스를 사용하도록 변경


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


