package shopping.infra

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import shopping.domain.Product
import shopping.domain.ProductName

@Repository
class ProductRepository(private val jdbcTemplate: JdbcTemplate) {
    fun save(product: Product): Product {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            { con ->
                val ps =
                    con.prepareStatement(
                        "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)",
                        arrayOf("id"),
                    )
                ps.setString(1, product.name.name)
                ps.setInt(2, product.price)
                ps.setString(3, product.imageUrl)
                ps
            },
            keyHolder,
        )
        val id = keyHolder.key!!.toLong()
        return Product(id, product.name, product.price, product.imageUrl)
    }

    fun update(product: Product) {
        jdbcTemplate.update(
            "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?",
            product.name.name,
            product.price,
            product.imageUrl,
            product.id,
        )
    }

    fun delete(id: Long) {
        jdbcTemplate.update("DELETE FROM product WHERE id = ?", id)
    }

    fun findAll(): List<Product> {
        return jdbcTemplate.query(
            "SELECT id, name, price, image_url FROM product",
        ) { rs, _ ->
            Product(
                id = rs.getLong("id"),
                name = ProductName(rs.getString("name")),
                price = rs.getInt("price"),
                imageUrl = rs.getString("image_url"),
            )
        }
    }

    fun findById(id: Long): Product? {
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
