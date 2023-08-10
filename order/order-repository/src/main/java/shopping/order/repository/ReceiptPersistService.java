package shopping.order.repository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import shopping.mart.domain.usecase.product.ProductUseCase;
import shopping.mart.domain.usecase.product.response.ProductResponse;
import shopping.mart.domain.Product;
import shopping.order.domain.Receipt;
import shopping.order.domain.repository.ReceiptRepository;
import shopping.order.repository.entity.ReceiptEntity;
import shopping.order.repository.entity.ReceiptProductEntity;

@Repository
public class ReceiptPersistService implements ReceiptRepository {

    private final ReceiptJpaRepository receiptJpaRepository;
    private final ProductUseCase productUseCase;

    public ReceiptPersistService(ReceiptJpaRepository receiptJpaRepository,
            ProductUseCase productUseCase) {
        this.receiptJpaRepository = receiptJpaRepository;
        this.productUseCase = productUseCase;
    }

    @Override
    public long persist(Receipt receipt) {
        ReceiptEntity receiptEntity = receiptJpaRepository.save(new ReceiptEntity(receipt));
        return receiptEntity.getId();
    }

    @Override
    public List<Receipt> findAllByUserId(long userId) {
        List<ReceiptEntity> receiptEntities = receiptJpaRepository.findAllByUserId(userId);
        List<ProductResponse> productResponses = productUseCase.findAllProducts();

        List<Receipt> receipts = new ArrayList<>();
        for (ReceiptEntity receiptEntity : receiptEntities) {
            List<Product> products = toProducts(productResponses, receiptEntity.getReceiptProductEntities());
            Receipt receipt = receiptEntity.toDomain(products);
            receipts.add(receipt);
        }
        return receipts;
    }

    @Override
    public Optional<Receipt> findByIdAndUserId(long id, long userId) {
        ReceiptEntity receiptEntity = receiptJpaRepository.getReferenceByIdAndUserId(id, userId);

        if (receiptEntity == null) {
            return Optional.empty();
        }

        List<ProductResponse> productResponses = productUseCase.findAllProducts();
        List<Product> products = toProducts(productResponses, receiptEntity.getReceiptProductEntities());
        Receipt receipt = receiptEntity.toDomain(products);

        return Optional.of(receipt);
    }

    private List<Product> toProducts(List<ProductResponse> productResponses,
            List<ReceiptProductEntity> receiptProductEntities) {

        List<Product> products = new ArrayList<>();
        for (ReceiptProductEntity receiptProductEntity : receiptProductEntities) {
            ProductResponse productResponse = getMatchedProductResponse(receiptProductEntity.getProductId(),
                    productResponses);
            products.add(new Product(productResponse.getId(), productResponse.getName(), productResponse.getImageUrl(),
                    productResponse.getPrice()));
        }
        return products;
    }

    private ProductResponse getMatchedProductResponse(long productId, List<ProductResponse> productResponses) {
        return productResponses.stream()
                .filter(productResponse -> productResponse.getId().equals(productId))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(
                        MessageFormat.format("productId \"{0}\" 에 해당하는 product를 찾을 수 없습니다.", productId)));
    }
}
