package kr.hhplus.be.server.test.fixture.point;

import kr.hhplus.be.server.domain.point.OriginType;
import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.domain.point.PointCommand;
import kr.hhplus.be.server.test.fixture.FixtureScenario;
import kr.hhplus.be.server.test.fixture.Fixtures;
import net.jqwik.api.Tuple;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;

public enum PointCommandDecreaseScenario implements FixtureScenario<PointCommandDecreaseScenario, Tuple.Tuple2<PointCommand.Decrease, Point>> {

    SUCCESS {
        @Override
        public Tuple.Tuple2<PointCommand.Decrease, Point> createOne() {
            Tuple.Tuple2<Point, Long> tuple = PointAmountScenario.SUCCESS_ON_DECREASE_BY_LESS_AMOUNT.createOne();
            Point point = tuple.get1();
            Long amount = tuple.get2();

            PointCommand.Decrease command = Fixtures.getFixtureMonkey()
                    .giveMeBuilder(PointCommand.Decrease.class)
                    .set(javaGetter(PointCommand.Increase::getUserId), point.getUserId())
                    .set(javaGetter(PointCommand.Increase::getAmount), amount)
                    .set(javaGetter(PointCommand.Increase::getOriginType), OriginType.PAYMENT)
                    .build()
                    .sample();


            return Tuple.of(command, point);
        }
    },

}
