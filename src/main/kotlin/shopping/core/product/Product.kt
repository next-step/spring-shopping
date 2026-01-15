package shopping.core.product

// 추후 가능하다면 Price 일급객체 선언해서 사용 가능
class Product(
    val name: String,
    val price: Long,
    val imageUrl: String,
    val id: Long = 0L,
) {
    fun copy(id: Long): Product = Product(this.name, this.price, this.imageUrl, id)
}
