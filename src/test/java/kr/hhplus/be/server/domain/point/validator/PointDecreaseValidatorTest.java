package kr.hhplus.be.server.domain.point.validator;

import kr.hhplus.be.server.common.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MockitoSettings
class PointDecreaseValidatorTest {

    private PointDecreaseValidator sut;

    @BeforeEach
    void setUp() {
        sut = new PointDecreaseValidator();
    }

    @DisplayName("검증할 액션인지 찾을 때")
    @Nested
    class Supports {

        @DisplayName("'DECREASE'면 수행한다.")
        @ParameterizedTest
        @CsvSource(textBlock = """
                DECREASE | true
                INCREASE | false
                """, delimiter = '|')
        void success(PointValidationContext.ActionType actionType, boolean expected) {
            // given
            PointValidationContext context = ImmutablePointValidationContext.builder()
                    .balance(new Random().nextLong(0, Long.MAX_VALUE))
                    .amount(new Random().nextLong(0, Long.MAX_VALUE))
                    .actionType(actionType)
                    .build();

            // when
            boolean supported = sut.supports(context);

            // then
            assertThat(supported).isEqualTo(expected);
        }

    }

    @DisplayName("포인트 감소를 검증할 때")
    @Nested
    class Validate {

        @DisplayName("감소분과 잔액이 올바르면 성공한다.")
        @ParameterizedTest
        @CsvSource(textBlock = """
                1000 | 1000
                1000 | 999
                2000 | 1000
                """, delimiter = '|')
        void success(long balance, long amount) {
            // given
            PointValidationContext context = ImmutablePointValidationContext.builder()
                    .balance(balance)
                    .amount(amount)
                    .actionType(PointValidationContext.ActionType.DECREASE)
                    .build();

            // when & then
            sut.validate(context);
        }

        @DisplayName("감소분이 0이하면 실패한다.")
        @ParameterizedTest
        @ValueSource(longs = {-10000, -5000, -1000, -1, 0})
        void fail1(long amount) {
            // given
            long balance = new Random().nextLong(0, Long.MAX_VALUE);
            PointValidationContext context = ImmutablePointValidationContext.builder()
                    .balance(balance)
                    .amount(amount)
                    .actionType(PointValidationContext.ActionType.DECREASE)
                    .build();

            // when & then
            assertThatThrownBy(() -> sut.validate(context))
                    .isInstanceOf(BusinessException.class);
        }

        @DisplayName("잔액이 감소분보다 작으면 실패한다.")
        @ParameterizedTest
        @CsvSource(textBlock = """
                0    | 1
                999  | 1000
                1000 | 2000
                """, delimiter = '|')
        void fail2(long balance, long amount) {
            // given
            PointValidationContext context = ImmutablePointValidationContext.builder()
                    .balance(balance)
                    .amount(amount)
                    .actionType(PointValidationContext.ActionType.DECREASE)
                    .build();

            // when & then
            assertThatThrownBy(() -> sut.validate(context))
                    .isInstanceOf(BusinessException.class);
        }

    }

}
