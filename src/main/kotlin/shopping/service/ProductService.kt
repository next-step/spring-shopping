package shopping.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import shopping.domain.Product
import shopping.dto.ProductRequest
import shopping.repository.ProductRepository

@Service
class ProductService(
    private val repository: ProductRepository) {

    fun getProducts(): List<Product> = repository.findAll()
    fun getProduct(id: Long): Product = findProduct(id)

    @Transactional
    fun addProduct(request: ProductRequest): Product {
        val product = Product(request.name, request.price, request.imageUrl)
        return repository.save(product)
    }

    @Transactional
    fun update(id: Long, request: ProductRequest): Product {
        findProduct(id)
        return repository.save(Product(request.name, request.price, request.imageUrl, id))
    }

    @Transactional
    fun delete(id: Long) {
        findProduct(id)
        repository.deleteById(id)
    }

    private fun findProduct(id: Long): Product =
        repository.findById(id)
            .orElseThrow { NoSuchElementException("상품(id=$id)이 존재하지 않습니다.") }
}