package kr.hhplus.be.server.test.fixture.point;

import kr.hhplus.be.server.domain.point.OriginType;
import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.domain.point.PointCommand;
import kr.hhplus.be.server.test.fixture.FixtureScenario;
import kr.hhplus.be.server.test.fixture.Fixtures;
import net.jqwik.api.Tuple;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static net.jqwik.api.Arbitraries.longs;

public enum PointCommandIncreaseScenario implements FixtureScenario<PointCommandIncreaseScenario, Tuple.Tuple2<PointCommand.Increase, Point>> {

    SUCCESS {
        @Override
        public Tuple.Tuple2<PointCommand.Increase, Point> createOne() {
            Point point = PointScenario.SUCCESS.createOne();

            PointCommand.Increase command = Fixtures.getFixtureMonkey()
                    .giveMeBuilder(PointCommand.Increase.class)
                    .set(javaGetter(PointCommand.Increase::getUserId), point.getUserId())
                    .set(javaGetter(PointCommand.Increase::getAmount), longs().greaterOrEqual(1))
                    .set(javaGetter(PointCommand.Increase::getOriginType), OriginType.CHARGE)
                    .build()
                    .sample();


            return Tuple.of(command, point);
        }
    },

}
