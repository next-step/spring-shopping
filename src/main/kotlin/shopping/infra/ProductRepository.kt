package shopping.infra

import org.springframework.jdbc.core.JdbcTemplate
import shopping.domain.Product
import shopping.domain.ProductName

class ProductRepository(private val jdbcTemplate: JdbcTemplate) {
    fun saveProduct(product: Product): Product {
        jdbcTemplate.update(
            "INSERT INTO product (id, name, price, image_url) VALUES (?, ?, ?, ?)",
            product.id,
            product.name.name,
            product.price,
            product.imageUrl,
        )
        return product
    }

    fun getById(id: Long): Product? {
        return jdbcTemplate.query(
            "SELECT id, name, price, image_url FROM product WHERE id = ?",
            { rs, _ ->
                Product(
                    id = rs.getLong("id"),
                    name = ProductName(rs.getString("name")),
                    price = rs.getInt("price"),
                    imageUrl = rs.getString("image_url"),
                )
            },
            id,
        ).firstOrNull()
    }
}
