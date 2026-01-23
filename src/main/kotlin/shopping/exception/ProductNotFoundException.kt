package shopping.exception

class ProductNotFoundException(id: Long) : NotFoundException("product", id)
