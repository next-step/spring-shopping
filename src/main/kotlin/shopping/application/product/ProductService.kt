package shopping.application.product

import org.springframework.stereotype.Service
import shopping.application.product.validator.ProductValidator
import shopping.core.product.Product
import shopping.core.product.ProductRepository
import shopping.web.product.response.ProductResponse
import shopping.web.product.response.ProductsResponse

@Service
class ProductService(
    val productRepository: ProductRepository,
    val productValidator: ProductValidator,
) {
    fun findAll(): ProductsResponse = ProductsResponse.fromDomain(productRepository.findAll())

    fun findById(id: Long): ProductResponse {
        val product = productRepository.findById(id).orElseThrow { IllegalArgumentException("Product not found") }
        return ProductResponse.fromDomain(product)
    }

    fun save(product: Product): ProductResponse {
        require(!productValidator.containsProfanity(product.name)) { "Product name is invalid" }
        return ProductResponse.fromDomain(productRepository.save(product))
    }

    fun deleteById(id: Long) = productRepository.deleteById(id)
}
