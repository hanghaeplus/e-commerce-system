package kr.hhplus.be.server.domain.point;

import kr.hhplus.be.server.test.fixture.Fixtures;
import kr.hhplus.be.server.test.fixture.point.PointCommandDecreaseScenario;
import kr.hhplus.be.server.test.fixture.point.PointCommandIncreaseScenario;
import net.jqwik.api.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
            Tuple.Tuple2<PointCommand.Increase, Point> tuple = Fixtures.on(PointCommandIncreaseScenario.SUCCESS);
            PointCommand.Increase command = tuple.get1();
            Point givenPoint = tuple.get2();
            Long balance = givenPoint.getBalance();

            given(pointRepository.findPointByUserId(command.getUserId()))
                    .willReturn(Optional.of(givenPoint));

            // when
            Point point = sut.increase(command);

            // then
            long expectedBalance = balance + command.getAmount();

            // 포인트를 영속화해야 한다.
            ArgumentCaptor<Point> pointCaptor = ArgumentCaptor.forClass(Point.class);
            verify(pointRepository, times(1)).savePoint(pointCaptor.capture());

            // 전달받은 값만큼 사용자의 포인트를 증가하여 영속화 레이어에 넘겨야 한다.
            assertThat(pointCaptor.getValue())
                    .isNotNull()
                    .returns(command.getUserId(), Point::getUserId)
                    .returns(expectedBalance, Point::getBalance);

            // 포인트 이력을 영속화해야 한다.
            ArgumentCaptor<PointHistory> pointHistoryCaptor = ArgumentCaptor.forClass(PointHistory.class);
            verify(pointRepository, times(1)).savePointHistory(pointHistoryCaptor.capture());

            // 전달받은 값 그대로 포인트 이력을 영속화 레이어에 넘겨야 한다.
            assertThat(pointHistoryCaptor.getValue())
                    .isNotNull()
                    .returns(command.getUserId(), PointHistory::getUserId)
                    .returns(command.getAmount(), PointHistory::getAmount)
                    .returns(OriginType.CHARGE, PointHistory::getOriginType);

            // 반환한 포인트는 전달받은 값만큼 증가되어야 한다.
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
            Tuple.Tuple2<PointCommand.Decrease, Point> tuple = Fixtures.on(PointCommandDecreaseScenario.SUCCESS);
            PointCommand.Decrease command = tuple.get1();
            Point givenPoint = tuple.get2();
            Long balance = givenPoint.getBalance();

            given(pointRepository.findPointByUserId(command.getUserId()))
                    .willReturn(Optional.of(givenPoint));

            // when
            Point point = sut.decrease(command);

            // then
            long expectedBalance = balance - command.getAmount();

            // 포인트를 영속화해야 한다.
            ArgumentCaptor<Point> pointCaptor = ArgumentCaptor.forClass(Point.class);
            verify(pointRepository, times(1)).savePoint(pointCaptor.capture());

            // 전달받은 값만큼 사용자의 포인트를 감소하여 영속화 레이어에 넘겨야 한다.
            assertThat(pointCaptor.getValue())
                    .isNotNull()
                    .returns(command.getUserId(), Point::getUserId)
                    .returns(expectedBalance, Point::getBalance);

            // 포인트 이력을 영속화해야 한다.
            ArgumentCaptor<PointHistory> pointHistoryCaptor = ArgumentCaptor.forClass(PointHistory.class);
            verify(pointRepository, times(1)).savePointHistory(pointHistoryCaptor.capture());

            // 전달받은 값 그대로 포인트 이력을 영속화 레이어에 넘겨야 한다.
            assertThat(pointHistoryCaptor.getValue())
                    .isNotNull()
                    .returns(command.getUserId(), PointHistory::getUserId)
                    .returns(command.getAmount(), PointHistory::getAmount)
                    .returns(OriginType.PAYMENT, PointHistory::getOriginType);

            // 반환한 포인트는 전달받은 값만큼 감소되어야 한다.
            assertThat(point)
                    .isNotNull()
                    .returns(command.getUserId(), Point::getUserId)
                    .returns(expectedBalance, Point::getBalance);
        }

    }

}
