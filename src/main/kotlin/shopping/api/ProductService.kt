package shopping.api

import org.springframework.stereotype.Service
import shopping.domain.Product
import shopping.domain.ProductNameFactory
import java.util.concurrent.atomic.AtomicLong

@Service
class ProductService(
    private val productNameFactory: ProductNameFactory,
) {
    private val ids: AtomicLong = AtomicLong(0)
    private val products: MutableMap<Long, Product> = mutableMapOf()

    fun getProducts(): List<ProductResponse> = products.values.map { it.toResponse() }

    fun addProduct(request: ProductRequest): ProductResponse {
        val id = ids.incrementAndGet()
        val product = Product(id, productNameFactory.create(request.name), request.price, request.imageUrl)
        products[id] = product
        return product.toResponse()
    }

    fun updateProduct(request: UpdateRequest): ProductResponse {
        getSingleProduct(request.id)
        val updated = Product(
            id = request.id,
            name = productNameFactory.create(request.name),
            price = request.price,
            imageUrl = request.imageUrl,
        )
        products[updated.id] = updated
        return updated.toResponse()
    }

    fun getSingleProduct(id: Long): ProductResponse {
        return products[id]?.toResponse() ?: throw ProductNotFoundException(id)
    }
}
