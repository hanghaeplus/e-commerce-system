package kr.hhplus.be.server.test.fixture.point;

import kr.hhplus.be.server.domain.point.PointHistory;
import kr.hhplus.be.server.test.fixture.FixtureScenario;
import kr.hhplus.be.server.test.fixture.Fixtures;

import java.util.List;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static net.jqwik.api.Arbitraries.longs;

public enum PointHistoryScenario implements FixtureScenario<PointHistoryScenario, PointHistory> {

    VALID {
        @Override
        public PointHistory createOne() {
            return Fixtures.getFixtureMonkey()
                    .giveMeBuilder(PointHistory.class)
                    .set(javaGetter(PointHistory::getId), longs().greaterOrEqual(1))
                    .set(javaGetter(PointHistory::getUserId), longs().greaterOrEqual(1))
                    .set(javaGetter(PointHistory::getAmount), longs().between(1, 100_000))
                    .build()
                    .sample();
        }

        @Override
        public List<PointHistory> createMany(int maxSize) {
            Long userId = longs().greaterOrEqual(1).sample();

            return Fixtures.getFixtureMonkey()
                    .giveMeBuilder(PointHistory.class)
                    .set(javaGetter(PointHistory::getId), longs().greaterOrEqual(1))
                    .set(javaGetter(PointHistory::getUserId), userId)
                    .set(javaGetter(PointHistory::getAmount), longs().between(1, 100_000))
                    .build()
                    .list()
                    .ofMaxSize(maxSize)
                    .sample();
        }
    },

}
