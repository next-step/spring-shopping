package shopping.mart.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.mart.repository.entity.ProductEntity;

interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByName(String name);

}
