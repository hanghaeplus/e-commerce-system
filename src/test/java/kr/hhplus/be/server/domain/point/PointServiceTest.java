package kr.hhplus.be.server.domain.point;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import kr.hhplus.be.server.domain.point.validator.PointValidators;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@MockitoSettings
class PointServiceTest {

    @InjectMocks
    private PointService sut;

    @Mock
    private PointRepository pointRepository;
    @Mock
    private PointValidators pointValidators;

    @DisplayName("포인트를 조회할 때")
    @Nested
    class GetPoint {

        @DisplayName("사용자의 포인트를 가져온다.")
        @Test
        void success() {
            // given
            long userId = new Random().nextLong(1L, Long.MAX_VALUE);
            PointCommand.Point command = PointCommand.Point.builder()
                    .userId(userId)
                    .build();

            given(pointRepository.findPointByUserId(command.getUserId()))
                    .willReturn(Optional.of(
                            Point.builder()
                                    .userId(command.getUserId())
                                    .balance(1000L)
                                    .build()
                    ));

            // when
            Point point = sut.getPoint(command);

            // then
            assertThat(point)
                    .isNotNull()
                    .returns(command.getUserId(), Point::getUserId)
                    .returns(1000L, Point::getBalance);
        }

        @DisplayName("사용자의 포인트가 생성되지 않았으면 실패한다.")
        @Test
        void fail() {
            // given
            long userId = new Random().nextLong(1L, Long.MAX_VALUE);
            PointCommand.Point command = PointCommand.Point.builder()
                    .userId(userId)
                    .build();
            given(pointRepository.findPointByUserId(command.getUserId()))
                    .willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> sut.getPoint(command));
        }

    }

    // -------------------------------------------------------------------------------------------------

    @DisplayName("포인트를 증가할 때")
    @Nested
    class Increase {

        @DisplayName("증가분만큼 포인트를 증가한다.")
        @ParameterizedTest
        @CsvSource(textBlock = """
                0    | 1000 | 1000
                999  | 1    | 1000
                1000 | 1000 | 2000
                """, delimiter = '|')
        void success(long balance, long amount, long expectedBalance) {
            // given
            long userId = new Random().nextLong(1L, Long.MAX_VALUE);
            PointCommand.Increase command = PointCommand.Increase.builder()
                    .userId(userId)
                    .amount(amount)
                    .originType(PointHistory.OriginType.CHARGE)
                    .build();

            given(pointRepository.findPointByUserId(command.getUserId()))
                    .willReturn(Optional.of(
                            Point.builder()
                                    .userId(command.getUserId())
                                    .balance(balance)
                                    .build()
                    ));

            // when
            Point point = sut.increase(command);

            // then
            verify(pointValidators, times(1)).validate(any());
            verify(pointRepository, times(1)).savePoint(any());
            verify(pointRepository, times(1)).savePointHistory(any());

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
        @ParameterizedTest
        @CsvSource(textBlock = """
                1000 | 1000 | 0
                1000 | 999  | 1
                2000 | 1000 | 1000
                """, delimiter = '|')
        void success(long balance, long amount, long expectedBalance) {
            // given
            long userId = new Random().nextLong(1L, Long.MAX_VALUE);
            PointCommand.Decrease command = PointCommand.Decrease.builder()
                    .userId(userId)
                    .amount(amount)
                    .originType(PointHistory.OriginType.PAYMENT)
                    .build();

            given(pointRepository.findPointByUserId(command.getUserId()))
                    .willReturn(Optional.of(
                            Point.builder()
                                    .userId(command.getUserId())
                                    .balance(balance)
                                    .build()
                    ));

            // when
            Point point = sut.decrease(command);

            // then
            verify(pointValidators, times(1)).validate(any());
            verify(pointRepository, times(1)).savePoint(any());
            verify(pointRepository, times(1)).savePointHistory(any());

            assertThat(point)
                    .isNotNull()
                    .returns(command.getUserId(), Point::getUserId)
                    .returns(expectedBalance, Point::getBalance);
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
            long userId = new Random().nextLong(1L, Long.MAX_VALUE);
            PointCommand.History command = PointCommand.History.builder()
                    .userId(userId)
                    .build();

            given(pointRepository.findPointHistoriesByUserId(command.getUserId()))
                    .willReturn(List.of(
                            PointHistory.builder()
                                    .userId(command.getUserId())
                                    .amount(1000L)
                                    .build()
                    ));

            // when
            List<PointHistory> histories = sut.getPointHistories(command);

            // then
            assertThat(histories)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSize(1)
                    .allMatch(it -> it.getUserId().equals(userId));
        }

        @DisplayName("사용자의 포인트 엔터티가 생성되지 않았으면 실패한다.")
        @Test
        void fail() {
            // given
            long userId = new Random().nextLong(1L, Long.MAX_VALUE);
            PointCommand.Point command = PointCommand.Point.builder()
                    .userId(userId)
                    .build();
            given(pointRepository.findPointByUserId(command.getUserId()))
                    .willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> sut.getPoint(command));
        }

    }

}
