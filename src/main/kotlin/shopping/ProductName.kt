package shopping

private val ALLOWED_PATTERN = Regex("^[a-zA-Z0-9가-힣\\s()\\[\\]+\\-&/_]*$")

class ProductName(val name: String) {
    init {
        require(name.length <= 15) {
            "[ERROR] ProductName should have at least 15 characters"
        }

        require(name.matches(ALLOWED_PATTERN)) {
            "[ERROR] ProductName should contain only alphanumeric characters"
        }
    }
}
