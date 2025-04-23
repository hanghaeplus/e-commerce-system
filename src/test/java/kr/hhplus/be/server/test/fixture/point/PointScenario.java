package kr.hhplus.be.server.test.fixture.point;

import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.test.fixture.FixtureScenario;
import kr.hhplus.be.server.test.fixture.Fixtures;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static net.jqwik.api.Arbitraries.longs;

public enum PointScenario implements FixtureScenario<PointScenario, Point> {

    SUCCESS {
        @Override
        public Point createOne() {
            return Fixtures.getFixtureMonkey()
                    .giveMeBuilder(Point.class)
                    .set(javaGetter(Point::getUserId), longs().greaterOrEqual(1))
                    .set(javaGetter(Point::getBalance), longs().between(0, 1_000_000))
                    .build()
                    .sample();
        }
    },

}
