package kr.hhplus.be.server.test.fixture.point;

import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.test.fixture.FixtureScenario;
import kr.hhplus.be.server.test.fixture.Fixtures;
import net.jqwik.api.Tuple;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static net.jqwik.api.Arbitraries.longs;

public enum PointAmountScenario implements FixtureScenario<PointAmountScenario, Tuple.Tuple2<Point, Long>> {

    SUCCESS_ON_INCREASE {
        @Override
        public Tuple.Tuple2<Point, Long> createOne() {
            Point point = PointScenario.SUCCESS.createOne();
            Long amount = longs().between(1, 100_000).sample();

            return Tuple.of(point, amount);
        }
    },

    // -------------------------------------------------------------------------------------------------

    SUCCESS_ON_DECREASE_BY_LESS_AMOUNT {
        @Override
        public Tuple.Tuple2<Point, Long> createOne() {
            Long amount = longs().between(1, 100_000).sample();

            Point point = Fixtures.getFixtureMonkey()
                    .giveMeBuilder(Point.class)
                    .set(javaGetter(Point::getUserId), longs().greaterOrEqual(1))
                    .set(javaGetter(Point::getBalance), longs().greaterOrEqual(amount + 1))
                    .build()
                    .sample();

            return Tuple.of(point, amount);
        }
    },

    // -------------------------------------------------------------------------------------------------

    SUCCESS_ON_DECREASE_BY_SAME_BALANCE {
        @Override
        public Tuple.Tuple2<Point, Long> createOne() {
            Long amount = longs().between(1, 100_000).sample();

            Point point = Fixtures.getFixtureMonkey()
                    .giveMeBuilder(Point.class)
                    .set(javaGetter(Point::getUserId), longs().greaterOrEqual(1))
                    .set(javaGetter(Point::getBalance), amount)
                    .build()
                    .sample();

            return Tuple.of(point, amount);
        }
    },

    // -------------------------------------------------------------------------------------------------

    FAIL_ON_ZERO_AMOUNT {
        @Override
        public Tuple.Tuple2<Point, Long> createOne() {
            Point point = PointScenario.SUCCESS.createOne();
            return Tuple.of(point, 0L);
        }
    },

    // -------------------------------------------------------------------------------------------------

    FAIL_ON_NEGATIVE_AMOUNT {
        @Override
        public Tuple.Tuple2<Point, Long> createOne() {
            Point point = PointScenario.SUCCESS.createOne();
            Long amount = longs().between(-100_000, -1).sample();

            return Tuple.of(point, amount);
        }
    },

    // -------------------------------------------------------------------------------------------------

    FAIL_ON_DECREASE_BY_GREATER_AMOUNT {
        @Override
        public Tuple.Tuple2<Point, Long> createOne() {
            Long amount = longs().between(1, 100_000).sample();

            Point point = Fixtures.getFixtureMonkey()
                    .giveMeBuilder(Point.class)
                    .set(javaGetter(Point::getUserId), longs().greaterOrEqual(1))
                    .set(javaGetter(Point::getBalance), amount - 1)
                    .build()
                    .sample();

            return Tuple.of(point, amount);
        }
    },

}
