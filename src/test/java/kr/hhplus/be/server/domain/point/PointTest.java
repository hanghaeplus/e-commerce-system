package kr.hhplus.be.server.domain.point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.test.util.ObjectMother;

import static org.assertj.core.api.Assertions.*;

class PointTest {

    @DisplayName("포인트 증가할 때")
    @Nested
    class Increase {

        @DisplayName("증가분이 올바르면 성공한다.")
        @Test
        void success() {
            // given
            Point point = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Point.class)
                    .setPostCondition(it -> it.getBalance() >= 0)
                    .build()
                    .sample();

            long amount = ObjectMother.getPositiveLong();

            // when & then
            point.increase(amount);
        }

        @DisplayName("증가분이 0이면 실패한다.")
        @Test
        void fail1() {
            // given
            Point point = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Point.class)
                    .setPostCondition(it -> it.getBalance() >= 0)
                    .build()
                    .sample();

            long amount = 0L;

            // when & then
            assertThatThrownBy(() -> point.increase(amount))
                    .isInstanceOf(BusinessException.class);
        }

        @DisplayName("증가분이 음수면 실패한다.")
        @Test
        void fail2() {
            // given
            Point point = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Point.class)
                    .setPostCondition(it -> it.getBalance() >= 0)
                    .build()
                    .sample();

            long amount = ObjectMother.getNegativeLong();

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
            Point point = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Point.class)
                    .setPostCondition(it -> it.getBalance() > 1)
                    .build()
                    .sample();

            long amount = point.getBalance() - 1;

            // when & then
            point.decrease(amount);
        }

        @DisplayName("잔액이 감소분과 같으면 성공한다.")
        @Test
        void success2() {
            // given
            Point point = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Point.class)
                    .setPostCondition(it -> it.getBalance() > 0)
                    .build()
                    .sample();

            long amount = point.getBalance();

            // when & then
            point.decrease(amount);
        }

        @DisplayName("감소분이 0이하면 실패한다.")
        @Test
        void fail1() {
            // given
            Point point = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Point.class)
                    .setPostCondition(it -> it.getBalance() >= 0)
                    .build()
                    .sample();

            long amount = 0L;

            // when & then
            assertThatThrownBy(() -> point.decrease(amount))
                    .isInstanceOf(BusinessException.class);
        }

        @DisplayName("잔액이 감소분보다 작으면 실패한다.")
        @Test
        void fail2() {
            // given
            Point point = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Point.class)
                    .setPostCondition(it -> it.getBalance() >= 0)
                    .build()
                    .sample();

            long amount = point.getBalance() + 1;

            // when & then
            assertThatThrownBy(() -> point.decrease(amount))
                    .isInstanceOf(BusinessException.class);
        }

    }

}
