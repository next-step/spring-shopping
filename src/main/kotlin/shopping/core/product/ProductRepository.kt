package shopping.core.product

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

@Repository
class ProductRepository(
    @Value("\${spring.datasource.url}") val url: String,
) {
    private val connection: Connection = DriverManager.getConnection(url)

    @PostConstruct
    fun createTable() {
        val sql =
            "create table product (" +
                "id bigint auto_increment primary key, " +
                "name varchar(20) not null, " +
                "price bigint not null, image_url varchar(1000) not null" +
                ")"
        connection.createStatement()
            .use { statement -> statement.execute(sql) }
    }

    fun findAll(): List<Product> {
        val sql = "select * from product"

        connection.prepareStatement(sql).use { statement ->
            statement.executeQuery().use { resultSet ->
                val result = mutableListOf<Product>()
                while (resultSet.next()) {
                    result += Product.fromResultSet(resultSet)
                }
                return result
            }
        }
    }

    fun findById(id: Long): Product {
        val sql = "select * from product where id = ?"
        connection.prepareStatement(sql).use { statement ->
            statement.setLong(1, id)
            statement.executeQuery().use { resultSet ->
                return if (resultSet.next()) {
                    Product.fromResultSet(resultSet)
                } else {
                    throw IllegalArgumentException("Product not found")
                }
            }
        }
    }

    fun save(product: Product): Product {
        val sql = "insert into product (name, price, image_url) values (?, ?, ?)"
        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { statement ->
            statement.setString(1, product.name)
            statement.setLong(2, product.price)
            statement.setString(3, product.imageUrl)
            statement.executeUpdate()

            statement.generatedKeys.use { resultSet ->
                if (!resultSet.next()) {
                    throw IllegalStateException("저장에 실패했습니다.")
                }
                val id = resultSet.getLong(1)
                return findById(id)
            }
        }
    }

    fun update(product: Product): Product {
        val sql = "update product set name = ?, price = ?, image_url = ? where id = ?"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, product.name)
            statement.setLong(2, product.price)
            statement.setString(3, product.imageUrl)
            statement.setLong(4, product.id)
            statement.executeUpdate()

            return findById(product.id)
        }
    }

    fun deleteById(id: Long): Boolean {
        val sql = "delete from product where id = ?"
        connection.prepareStatement(sql).use { statement ->
            statement.setLong(1, id)
            return statement.executeUpdate() > 0
        }
    }

    fun deleteAll() {
        val sql = "truncate table product"
        connection.createStatement().use { statement ->
            statement.executeUpdate(sql)
        }
    }
}
