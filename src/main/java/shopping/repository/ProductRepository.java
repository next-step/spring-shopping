package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // TODO: 커스텀 예외를 위한 메소드 추가하기

}
