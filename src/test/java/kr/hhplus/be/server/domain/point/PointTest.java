package kr.hhplus.be.server.domain.point;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.test.fixture.Fixtures;
import kr.hhplus.be.server.test.fixture.point.PointAmountScenario;
import net.jqwik.api.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PointTest {

    @DisplayName("포인트 증가할 때")
    @Nested
    class Increase {

        @DisplayName("증가분이 올바르면 성공한다.")
        @Test
        void success() {
            // given
            Tuple.Tuple2<Point, Long> tuple = Fixtures.on(PointAmountScenario.SUCCESS_ON_INCREASE);
            Point point = tuple.get1();
            Long amount = tuple.get2();

            // when & then
            point.increase(amount);
        }

        @DisplayName("증가분이 0이면 실패한다.")
        @Test
        void fail1() {
            // given
            Tuple.Tuple2<Point, Long> tuple = Fixtures.on(PointAmountScenario.FAIL_ON_ZERO_AMOUNT);
            Point point = tuple.get1();
            Long amount = tuple.get2();

            // when & then
            assertThatThrownBy(() -> point.increase(amount))
                    .isInstanceOf(BusinessException.class);
        }

        @DisplayName("증가분이 음수면 실패한다.")
        @Test
        void fail2() {
            // given
            Tuple.Tuple2<Point, Long> tuple = Fixtures.on(PointAmountScenario.FAIL_ON_NEGATIVE_AMOUNT);
            Point point = tuple.get1();
            Long amount = tuple.get2();

            // when & then
            assertThatThrownBy(() -> point.increase(amount))
                    .isInstanceOf(BusinessException.class);
        }

    }

    // -------------------------------------------------------------------------------------------------

    @DisplayName("포인트를 감소할 때")
    @Nested
    class Decrease {

        @DisplayName("잔액이 감소분보다 크면 성공한다.")
        @Test
        void success1() {
            // given
            Tuple.Tuple2<Point, Long> tuple = Fixtures.on(PointAmountScenario.SUCCESS_ON_DECREASE_BY_LESS_AMOUNT);
            Point point = tuple.get1();
            Long amount = tuple.get2();

            // when & then
            point.decrease(amount);
        }

        @DisplayName("잔액이 감소분과 같으면 성공한다.")
        @Test
        void success2() {
            // given
            Tuple.Tuple2<Point, Long> tuple = Fixtures.on(PointAmountScenario.SUCCESS_ON_DECREASE_BY_SAME_BALANCE);
            Point point = tuple.get1();
            Long amount = tuple.get2();

            // when & then
            point.decrease(amount);
        }

        @DisplayName("감소분이 0이면 실패한다.")
        @Test
        void fail1() {
            // given
            Tuple.Tuple2<Point, Long> tuple = Fixtures.on(PointAmountScenario.FAIL_ON_ZERO_AMOUNT);
            Point point = tuple.get1();
            Long amount = tuple.get2();

            // when & then
            assertThatThrownBy(() -> point.decrease(amount))
                    .isInstanceOf(BusinessException.class);
        }

        @DisplayName("감소분이 음수면 실패한다.")
        @Test
        void fail2() {
            // given
            Tuple.Tuple2<Point, Long> tuple = Fixtures.on(PointAmountScenario.FAIL_ON_NEGATIVE_AMOUNT);
            Point point = tuple.get1();
            Long amount = tuple.get2();

            // when & then
            assertThatThrownBy(() -> point.decrease(amount))
                    .isInstanceOf(BusinessException.class);
        }

        @DisplayName("잔액이 감소분보다 작으면 실패한다.")
        @Test
        void fail3() {
            // given
            Tuple.Tuple2<Point, Long> tuple = Fixtures.on(PointAmountScenario.FAIL_ON_DECREASE_BY_GREATER_AMOUNT);
            Point point = tuple.get1();
            Long amount = tuple.get2();

            // when & then
            assertThatThrownBy(() -> point.decrease(amount))
                    .isInstanceOf(BusinessException.class);
        }

    }

}
