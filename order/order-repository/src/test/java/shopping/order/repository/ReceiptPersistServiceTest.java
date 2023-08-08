package shopping.order.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import shopping.mart.app.domain.Cart;
import shopping.order.app.domain.Order;
import shopping.order.app.domain.Receipt;
import shopping.order.repository.entity.ReceiptEntity;
import shopping.order.repository.entity.ReceiptProductEntity;

@DisplayName("ReceiptPersistService 클래스")
@ContextConfiguration(classes = ReceiptPersistService.class)
public class ReceiptPersistServiceTest extends JpaTest {

    @Autowired
    private ReceiptPersistService receiptPersistService;

    @Autowired
    private ReceiptJpaRepository receiptJpaRepository;

    @Nested
    @DisplayName("persist 메소드는")
    class persist_method {

        @Test
        @DisplayName("Receipt 도메인을 받아 영속화 한다.")
        void persist_receipt_when_receive_receipt() {
            // given
            Cart cart = DomainFixture.Cart.defaultCart();
            Order order = new Order(cart);
            Receipt receipt = order.purchase();
            ReceiptEntity expected = new ReceiptEntity(receipt);

            // when
            receiptPersistService.persist(receipt);
            List<ReceiptEntity> result = receiptJpaRepository.findAll();

            // then
            assertReceiptEntityExceptId(result.get(0), expected);
        }

        private void assertReceiptEntityExceptId(ReceiptEntity result, ReceiptEntity expected) {
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getTotalPrice()).isEqualTo(expected.getTotalPrice());
                assertExactlyReceiptProductEntity(result.getReceiptProductEntities(),
                        expected.getReceiptProductEntities());
            });
        }

        private void assertExactlyReceiptProductEntity(List<ReceiptProductEntity> result,
                List<ReceiptProductEntity> expected) {

            assertThat(result).hasSize(expected.size());
            SoftAssertions.assertSoftly(softAssertions -> {
                for (int i = 0; i < result.size(); i++) {
                    ReceiptProductEntity resultElement = result.get(i);
                    ReceiptProductEntity expectedElement = expected.get(i);
                    softAssertions.assertThat(resultElement.getQuantity()).isEqualTo(expectedElement.getQuantity());
                }
            });
        }
    }
}
