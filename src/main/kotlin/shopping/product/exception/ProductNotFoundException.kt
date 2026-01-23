package shopping.product.exception

import shopping.exception.NotFoundException

class ProductNotFoundException(id: Long) : NotFoundException("product", id)
