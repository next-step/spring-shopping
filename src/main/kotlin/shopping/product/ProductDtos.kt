package shopping.product

import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL

data class ProductRequest(
        @field:Size(min = 1, max = 15)
        val name: String,
        val price: Int,
        @field:URL
        val imageUrl: String
    ) {
    }

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
){
    constructor(product: Product) : this(
        id = product.id,
        name = product.name,
        price = product.price,
        imageUrl = product.imageUrl
    )
}
