package kr.hhplus.be.server.domain.point.validator;

import kr.hhplus.be.server.common.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MockitoSettings
class PointIncreaseValidatorTest {

    private PointIncreaseValidator sut;

    @BeforeEach
    void setUp() {
        sut = new PointIncreaseValidator();
    }

    @DisplayName("검증할 액션인지 찾을 때")
    @Nested
    class Supports {

        @DisplayName("'INCREASE'면 수행한다.")
        @ParameterizedTest
        @CsvSource(textBlock = """
                INCREASE | true
                DECREASE | false
                         | false
                """, delimiter = '|')
        void success(PointValidationContext.ActionType actionType, boolean expected) {
            // given
            PointValidationContext context = PointValidationContext.builder()
                    .actionType(actionType)
                    .build();

            // when
            boolean supported = sut.supports(context);

            // then
            assertThat(supported).isEqualTo(expected);
        }

    }

    @DisplayName("포인트 증가를 검증할 때")
    @Nested
    class Validate {

        @DisplayName("증가분이 올바르면 성공한다.")
        @ParameterizedTest
        @ValueSource(longs = {1, 1000, 5000, 10000})
        void success(long amount) {
            // given
            PointValidationContext context = PointValidationContext.builder()
                    .amount(amount)
                    .build();

            // when & then
            sut.validate(context);
        }

        @DisplayName("증가분이 0이하면 실패한다.")
        @ParameterizedTest
        @ValueSource(longs = {-10000, -5000, -1000, -1, 0})
        void fail1(long amount) {
            // given
            PointValidationContext context = PointValidationContext.builder()
                    .amount(amount)
                    .build();

            // when & then
            assertThatThrownBy(() -> sut.validate(context))
                    .isInstanceOf(BusinessException.class);
        }

    }

}
