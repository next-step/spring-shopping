package shopping.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.order.app.api.receipt.ReceiptUseCase;
import shopping.order.app.domain.exception.DoesNotFindReceiptException;
import shopping.order.app.spi.ReceiptRepository;

@ExtendWith(SpringExtension.class)
@DisplayName("ReceiptService 클래스")
@ContextConfiguration(classes = ReceiptService.class)
class ReceiptServiceTest {

    @Autowired
    private ReceiptUseCase receiptUseCase;

    @MockBean
    private ReceiptRepository receiptRepository;

    @Nested
    @DisplayName("getByIdAndUserId 메소드는")
    class get_receipt_by_id_and_user_id_method {

        @Test
        @DisplayName("receiptId와 userId에 일치하는 Receipt를 찾을 수 없는경우, DoesNotFindReceiptException을 던진다.")
        void throw_does_not_find_receipt_exception_when_cannot_find_matched_receipt() {
            // given
            long id = 1L;
            long userId = 2L;

            Mockito.when(receiptRepository.findByIdAndUserId(id, userId)).thenReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> receiptUseCase.getByIdAndUserId(id, userId));

            // then
            assertThat(exception).isInstanceOf(DoesNotFindReceiptException.class);
        }

    }

}
