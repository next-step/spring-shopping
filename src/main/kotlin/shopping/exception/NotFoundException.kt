package shopping.exception

open class NotFoundException(target: String, id: Long) : RuntimeException() {

    override val message: String = "$target $id Not found"
}
