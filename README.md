# spring-shopping

## 상품 목록 조회

### Request

``` 
URL : GET /products
```

### Request Header

```
Content-type : application/json
```

### Response

```json

{
  "products": [
    {
      "id": "long",
      "name": "name",
      "url": "image-url",
      "price": "string"
    },
    {
      "id": "long",
      "name": "name",
      "url": "image-url",
      "price": "string"
    }
    ...
  ]
}

```
