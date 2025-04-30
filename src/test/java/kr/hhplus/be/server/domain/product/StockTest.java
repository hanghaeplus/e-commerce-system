package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.test.util.ObjectMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockTest {

    @DisplayName("재고를 증가할 때")
    @Nested
    class Increase {

        @DisplayName("증가분이 올바르면 성공한다.")
        @Test
        void success() {
            // given
            Stock stock = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Stock.class)
                    .setPostCondition(it -> it.getQuantity() >= 0)
                    .build()
                    .sample();

            int amount = ObjectMother.getPositiveInt();

            // when & then
            stock.increase(amount);
        }

        @DisplayName("증가분이 0이면 실패한다.")
        @Test
        void fail1() {
            // given
            Stock stock = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Stock.class)
                    .setPostCondition(it -> it.getQuantity() >= 0)
                    .build()
                    .sample();

            int amount = 0;

            // when & then
            assertThatThrownBy(() -> stock.increase(amount))
                    .isInstanceOf(BusinessException.class);
        }

        @DisplayName("증가분이 음수면 실패한다.")
        @Test
        void fail2() {
            // given
            Stock stock = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Stock.class)
                    .setPostCondition(it -> it.getQuantity() >= 0)
                    .build()
                    .sample();

            int amount = ObjectMother.getNegativeInt();

            // when & then
            assertThatThrownBy(() -> stock.increase(amount))
                    .isInstanceOf(BusinessException.class);
        }

    }

    // -------------------------------------------------------------------------------------------------

    @DisplayName("재고를 감소할 때")
    @Nested
    class Decrease {

        @DisplayName("잔액이 감소분보다 크면 성공한다.")
        @Test
        void success1() {
            // given
            Stock stock = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Stock.class)
                    .setPostCondition(it -> it.getQuantity() > 1)
                    .build()
                    .sample();

            int amount = stock.getQuantity() - 1;

            // when & then
            stock.decrease(amount);
        }

        @DisplayName("잔액이 감소분과 같으면 성공한다.")
        @Test
        void success2() {
            // given
            Stock stock = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Stock.class)
                    .setPostCondition(it -> it.getQuantity() > 0)
                    .build()
                    .sample();

            int amount = stock.getQuantity();

            // when & then
            stock.decrease(amount);
        }

        @DisplayName("감소분이 0이하면 실패한다.")
        @Test
        void fail1() {
            // given
            Stock stock = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Stock.class)
                    .setPostCondition(it -> it.getQuantity() >= 0)
                    .build()
                    .sample();

            int amount = 0;

            // when & then
            assertThatThrownBy(() -> stock.decrease(amount))
                    .isInstanceOf(BusinessException.class);
        }

        @DisplayName("잔액이 감소분보다 작으면 실패한다.")
        @Test
        void fail2() {
            // given
            Stock stock = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Stock.class)
                    .setPostCondition(it -> it.getQuantity() >= 0)
                    .build()
                    .sample();

            int amount = stock.getQuantity() + 1;

            // when & then
            assertThatThrownBy(() -> stock.decrease(amount))
                    .isInstanceOf(BusinessException.class);
        }

    }

}