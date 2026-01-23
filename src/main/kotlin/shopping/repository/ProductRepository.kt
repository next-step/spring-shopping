package shopping.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import shopping.repository.model.Product

@Repository
class ProductRepository(private val jdbcTemplate: JdbcTemplate) {
    private val productRowMapper = RowMapper<Product> { rs, _ ->
        Product(
            id = rs.getLong("id"),
            name = rs.getString("name"),
            price = rs.getInt("price"),
            imageUrl = rs.getString("image_url"),
        )
    }

    fun save(product: Product): Product {
        return if (product.id == null) {
            insert(product)
        } else {
            update(product)
        }
    }

    private fun insert(product: Product): Product {
        val sql = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)"
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update({
            val ps = it.prepareStatement(sql, arrayOf("id"))
            ps.setString(1, product.name)
            ps.setInt(2, product.price)
            ps.setString(3, product.imageUrl)
            ps
        }, keyHolder)

        val id = keyHolder.key?.toLong() ?: throw IllegalStateException("Failed to retrieve generated key")
        product.id = id
        return product
    }

    private fun update(product: Product): Product {
        val sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?"

        jdbcTemplate.update(
            sql,
            product.name,
            product.price,
            product.imageUrl,
            product.id!!
        )

        return product;
    }

    fun findById(id: Long): Product? {
        val sql = "SELECT id, name, price, image_url FROM product WHERE id = ?"
        return jdbcTemplate.query(sql, productRowMapper, id).firstOrNull()
    }

    fun findAll(): List<Product> {
        val sql = "SELECT id, name, price, image_url FROM product"
        return jdbcTemplate.query(sql, productRowMapper)
    }

    fun deleteById(id: Long) {
        val sql = "DELETE FROM product WHERE id = ?"
        jdbcTemplate.update(sql, id)
    }
}
