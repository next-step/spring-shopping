# Dependencies

### Module dependencies
```mermaid
flowchart LR    

mart-controller --> mart-domain  
mart-repository --> mart-domain  
mart-service --> mart-domain  
mart-controller --> auth-domain  
mart-domain  

auth-service --> auth-domain  
auth-repository --> auth-domain
auth-controller --> auth-domain  
auth-domain
  
order-service --> mart-domain
order-service --> order-domain    
order-controller --> auth-domain  
order-controller --> order-domain  
order-exchange --> order-domain  
order-repository --> order-domain  
order-repository --> mart-domain
```

# API Documentation

## Auth

### Login

Endpoint: `POST /login/token`

유저 정보를 갖고 인증을 진행합니다.

#### Request Body

```json
{
    "password": "string",
    "email": "string"
}
```

| Field    | Type   | Description |
|----------|--------|------------|
| password | string | 유저 비밀번호    |
| email    | string | 유저의 이메일    |

#### Response

```json
{
    "accessToken": "string"
}
```

| Field       | Type   | Description |
|-------------|--------|------------|
| accessToken | string | 인증된 토큰     |

## Cart

### 카트에 제품 추가 (Add Product to Cart)

새로운 제품을 카트에 추가합니다.

- URL: `POST /carts`
- 요청 형식: JSON
- 응답: 201 Created

**요청 예시**

```json
{
    "productId": 12345
}
```

| 필드      | 타입   | 설명                   |
|-----------|--------|-----------------------|
| productId | long   | 추가할 제품의 아이디.   |

**응답 예시**

```
HTTP/1.1 201 Created
```

#### 카트에 담긴 제품 목록 조회 (Find All Products in Cart)

카트에 담긴 모든 제품들의 목록을 조회합니다.

- URL: `GET /carts`
- 응답: 200 OK

**응답 예시**

```json
{
    "cartId": 1,
    "products": [
        {
            "id": 12345,
            "name": "상품명",
            "imageUrl": "https://example.com/product_image.jpg",
            "count": 2
        },
        {
            "id": 67890,
            "name": "다른 상품",
            "imageUrl": "https://example.com/another_product_image.jpg",
            "count": 1
        }
    ]
}
```

| 필드      | 타입   | 설명                      |
|-----------|--------|--------------------------|
| cartId    | long   | 카트 아이디.              |
| products  | 배열   | 카트에 담긴 제품들의 목록.|

#### 카트에 담긴 제품 수량 업데이트 (Update Product Count in Cart)

카트에 담긴 제품의 수량을 업데이트합니다.

- URL: `PATCH /carts`
- 요청 형식: JSON
- 응답: 200 OK

**요청 예시**

```json
{
    "productId": 12345,
    "count": 3
}
```

| 필드      | 타입   | 설명                   |
|-----------|--------|-----------------------|
| productId | long   | 업데이트할 제품의 아이디.   |
| count     | int    | 업데이트할 제품의 수량.   |

**응답 예시**

```
HTTP/1.1 200 OK
```

#### 카트에서 제품 삭제하기

카트에서 특정 제품을 삭제합니다.

- URL: `DELETE /carts`
- 파라미터: `product-id` (삭제할 제품의 아이디)
- 응답: 204 No Content

**요청 예시**

```
DELETE /carts?product-id=12345
```

**응답 예시**

```
HTTP/1.1 204 No Content
```

#### 예외 처리

- 400 Bad Request `AlreadyExistProductException`: 이미 카트에 존재하는 제품을 추가할 때 발생하는 예외.
- 400 Bad Request `DoesNotExistProductException`: 카트에 존재하지 않는 제품을 업데이트 또는 삭제할 때 발생하는 예외.
- 400 Bad Request `NegativeProductCountException`: 제품 수량이 음수인 경우 발생하는 예외.

## 주문 생성 API

주문을 생성하고, 주문에 대한 상세 정보를 확인할 수 있는 API입니다.


- URL: `POST /orders`
- 요청 헤더: Authorization: token
- 응답: 200 OK
- 응답 헤더: Location: /order-detail/{receiptId}


**응답 예시**

```
HTTP/1.1 200 OK 
Location: /order-detail/{receiptId}
```

### 예외 처리

- 400 Bad Request `EmptyCartException`: Cart가 비어있는상황에서 주문 요청할때
- 500 Internal Server Error `IllegalExchangeRateException` 환율이 0 미만 일때

## 내가 구매한 모든 구매내역(Receipt) 조회 API

내가 구매한 구매내역을 조회할 수 있습니다.

- URL: `GET /receipts`
- 요청 헤더: Authorization: token
- 응답: 200 OK

**응답 예시**

```json
{
  "id": 98765,
  "receiptProducts": [
    {
      "productId": 12345,
      "name": "Product Name 1",
      "price": "19099",
      "imageUrl": "/images/beer.jpeg",
      "quantity": 1
    },
    {
      "productId": 67890,
      "name": "Product Name 2",
      "price": "90000",
      "imageUrl": "/images/soju.png",
      "quantity": 3
    }
  ]
}
```

## 내가 구매한 측정 구매내역(Receipt) 조회 API

내가 구매한 구매내역중 특정 구매내역을 자세히 조회할 수 있습니카.

- USER: `GET /receipts/{receiptId}`
- 요청 헤더: Authorization: token
- 응답: 200 OK

**응답 예시**

``` json
{
  "id": 98765,
  "receiptDetailProducts": [
    {
      "productId": 12345,
      "name": "Product Name 1",
      "price": "19099",
      "imageUrl": "/images/beer.jpeg",
      "quantity": 1
    },
    {
      "productId": 67890,
      "name": "Product Name 2",
      "price": "90000",
      "imageUrl": "/images/soju.png",
      "quantity": 3
    }
  ],
  "totalPrice": "9999999",
  "exchangedPrice": "44.99",
  "exchangeRate": 0.90
}
```

예외 처리

- 400 Bad Request `DoesNotFindReceiptException` Receipt를 찾을 수 없을때
