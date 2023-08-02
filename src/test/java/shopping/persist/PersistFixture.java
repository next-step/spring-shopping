package shopping.persist;

import shopping.persist.entity.ProductEntity;

class PersistFixture {

    static class Product {

        static shopping.domain.Product withEntity(ProductEntity productEntity) {
            return new shopping.domain.Product(productEntity.getId(), productEntity.getName(),
                    productEntity.getImageUrl(),
                    productEntity.getPrice());
        }

    }

}
