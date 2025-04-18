package kr.hhplus.be.server.domain.point;

import kr.hhplus.be.server.test.util.ObjectMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@MockitoSettings
class PointServiceTest {

    @InjectMocks
    private PointService sut;

    @Mock
    private PointRepository pointRepository;

    // -------------------------------------------------------------------------------------------------

    @DisplayName("포인트를 증가할 때")
    @Nested
    class Increase {

        @DisplayName("증가분만큼 포인트를 증가한다.")
        @Test
        void success() {
            // given
            long balance = ObjectMother.getLong(0, 1_000_000);
            Point givenPoint = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Point.class)
                    .setPostCondition(it -> it.getUserId() > 0)
                    .set("balance", balance)
                    .build()
                    .sample();

            PointCommand.Increase command = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(PointCommand.Increase.class)
                    .set("userId", givenPoint.getUserId())
                    .set("originType", PointHistory.OriginType.CHARGE)
                    .setPostCondition(it -> 1000 <= it.getAmount() && it.getAmount() <= 100_000)
                    .build()
                    .sample();

            given(pointRepository.findPointByUserId(command.getUserId()))
                    .willReturn(Optional.of(givenPoint));

            // when
            Point point = sut.increase(command);

            // then
            long expectedBalance = balance + command.getAmount();

            ArgumentCaptor<Point> pointCaptor = ArgumentCaptor.forClass(Point.class);
            verify(pointRepository, times(1)).savePoint(pointCaptor.capture());
            assertThat(pointCaptor.getValue())
                    .isNotNull()
                    .returns(command.getUserId(), Point::getUserId)
                    .returns(expectedBalance, Point::getBalance);

            ArgumentCaptor<PointHistory> pointHistoryCaptor = ArgumentCaptor.forClass(PointHistory.class);
            verify(pointRepository, times(1)).savePointHistory(pointHistoryCaptor.capture());
            assertThat(pointHistoryCaptor.getValue())
                    .isNotNull()
                    .returns(command.getUserId(), PointHistory::getUserId)
                    .returns(command.getAmount(), PointHistory::getAmount)
                    .returns(PointHistory.OriginType.CHARGE, PointHistory::getOriginType);

            assertThat(point)
                    .isNotNull()
                    .returns(command.getUserId(), Point::getUserId)
                    .returns(expectedBalance, Point::getBalance);
        }

    }

    // -------------------------------------------------------------------------------------------------

    @DisplayName("포인트를 감소할 때")
    @Nested
    class Decrease {

        @DisplayName("감소분만큼 포인트를 감소한다.")
        @Test
        void success() {
            // given
            long balance = ObjectMother.getLong(100_000, 1_000_000);
            Point givenPoint = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Point.class)
                    .setPostCondition(it -> it.getUserId() > 0)
                    .set("balance", balance)
                    .build()
                    .sample();

            PointCommand.Decrease command = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(PointCommand.Decrease.class)
                    .set("userId", givenPoint.getUserId())
                    .set("originType", PointHistory.OriginType.PAYMENT)
                    .setPostCondition(it -> 1000 <= it.getAmount() && it.getAmount() <= 100_000)
                    .build()
                    .sample();

            given(pointRepository.findPointByUserId(command.getUserId()))
                    .willReturn(Optional.of(givenPoint));

            // when
            Point point = sut.decrease(command);

            // then
            long expectedBalance = balance - command.getAmount();

            ArgumentCaptor<Point> pointCaptor = ArgumentCaptor.forClass(Point.class);
            verify(pointRepository, times(1)).savePoint(pointCaptor.capture());
            assertThat(pointCaptor.getValue())
                    .isNotNull()
                    .returns(command.getUserId(), Point::getUserId)
                    .returns(expectedBalance, Point::getBalance);

            ArgumentCaptor<PointHistory> pointHistoryCaptor = ArgumentCaptor.forClass(PointHistory.class);
            verify(pointRepository, times(1)).savePointHistory(pointHistoryCaptor.capture());
            assertThat(pointHistoryCaptor.getValue())
                    .isNotNull()
                    .returns(command.getUserId(), PointHistory::getUserId)
                    .returns(command.getAmount(), PointHistory::getAmount)
                    .returns(PointHistory.OriginType.PAYMENT, PointHistory::getOriginType);

            assertThat(point)
                    .isNotNull()
                    .returns(command.getUserId(), Point::getUserId)
                    .returns(expectedBalance, Point::getBalance);
        }

    }

}
