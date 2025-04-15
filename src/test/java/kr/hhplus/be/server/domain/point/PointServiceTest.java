package kr.hhplus.be.server.domain.point;

import kr.hhplus.be.server.test.util.ObjectMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

    @DisplayName("포인트를 조회할 때")
    @Nested
    class GetPoint {

        @DisplayName("사용자의 포인트를 가져온다.")
        @Test
        void success() {
            // given
            PointCommand.Point command = ObjectMother.getFixtureMonkey()
                    .giveMeOne(PointCommand.Point.class);

            Point givenPoint = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(Point.class)
                    .set("userId", command.getUserId())
                    .setPostCondition(it -> it.getBalance() >= 0)
                    .build()
                    .sample();

            given(pointRepository.findPointByUserId(command.getUserId()))
                    .willReturn(Optional.of(givenPoint));

            // when
            Point point = sut.getPoint(command);

            // then
            assertThat(point)
                    .isNotNull()
                    .returns(command.getUserId(), Point::getUserId)
                    .returns(givenPoint.getBalance(), Point::getBalance);
        }

        @DisplayName("사용자의 포인트가 생성되지 않았으면 실패한다.")
        @Test
        void fail() {
            // given
            PointCommand.Point command = ObjectMother.getFixtureMonkey()
                    .giveMeOne(PointCommand.Point.class);

            given(pointRepository.findPointByUserId(command.getUserId()))
                    .willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> sut.getPoint(command));
        }

    }

    // -------------------------------------------------------------------------------------------------

    @DisplayName("포인트 이력을 조회할 때")
    @Nested
    class GetPointHistories {

        @DisplayName("사용자의 포인트 이력을 가져온다.")
        @Test
        void success() {
            // given
            PointCommand.History command = ObjectMother.getFixtureMonkey()
                    .giveMeOne(PointCommand.History.class);

            List<PointHistory> givenHistories = ObjectMother.getFixtureMonkey()
                    .giveMeBuilder(PointHistory.class)
                    .set("userId", command.getUserId())
                    .setPostCondition(it -> it.getAmount() > 0)
                    .build()
                    .sampleStream()
                    .limit(100)
                    .toList();

            given(pointRepository.findPointHistoriesByUserId(command.getUserId()))
                    .willReturn(givenHistories);

            // when
            List<PointHistory> histories = sut.getPointHistories(command);

            // then
            assertThat(histories)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSize(givenHistories.size())
                    .allMatch(it -> it.getUserId().equals(command.getUserId()));
        }

    }

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
            verify(pointRepository, times(1)).savePoint(any());
            verify(pointRepository, times(1)).savePointHistory(any());

            long expectedBalance = balance + command.getAmount();
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
            verify(pointRepository, times(1)).savePoint(any());
            verify(pointRepository, times(1)).savePointHistory(any());

            long expectedBalance = balance - command.getAmount();
            assertThat(point)
                    .isNotNull()
                    .returns(command.getUserId(), Point::getUserId)
                    .returns(expectedBalance, Point::getBalance);
        }

    }

}
