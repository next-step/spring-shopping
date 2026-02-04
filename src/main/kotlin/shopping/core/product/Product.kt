package shopping.core.product

import java.sql.ResultSet

class Product(
    val name: String,
    val price: Long,
    val imageUrl: String,
    val id: Long = 0L,
) {
    companion object {
        fun fromResultSet(resultSet: ResultSet): Product {
            return Product(
                name = resultSet.getString("name"),
                price = resultSet.getLong("price"),
                imageUrl = resultSet.getString("image_url"),
                id = resultSet.getLong("id"),
            )
        }
    }
}
