# spring-shopping

## 상품
- [X] 상품에는 이름, 가격, 이미지가 있다
- [X] 이미지는 파일이 아닌 url이다
- [X] 상품이름은 공백을 포함하여 최대 15자까지이다.
- [X]  특수문자는 다음 문자만 가능하다(( ), [ ], +, -, &, /, _)
- [X] 상품이름에는 비속어를 포함할 수 없다.
- [X] 상품을 조회하는 기능을 구현한다
- [X] 상품을 추가하는 기능을 구현한다
- [] 상품을 수정하는 기능을 구현한다
- [] 상품을 삭제하는 기능을 구현한다
- [] 상품 정보를 코틀린 컬렉션을 사용하여 메모리에 저장한다



## 상품 조회 예시
request
```bash
GET /api/products HTTP/1.1
```
response
```bash
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