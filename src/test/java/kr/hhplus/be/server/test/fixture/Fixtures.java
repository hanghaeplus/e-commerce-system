package kr.hhplus.be.server.test.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FailoverIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import lombok.Getter;

import java.util.List;

public final class Fixtures {

    @Getter
    private static final FixtureMonkey fixtureMonkey;

    static {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(new FailoverIntrospector(List.of(
                        FieldReflectionArbitraryIntrospector.INSTANCE,
                        BuilderArbitraryIntrospector.INSTANCE
                ), false))
                .defaultNotNull(true)
                .enableLoggingFail(false)
                .build();
    }

    public static <F> F on(FixtureScenario<?, F> scenario) {
        return scenario.createOne();
    }

    public static <F> List<F> on(FixtureScenario<?, F> scenario, int maxSize) {
        return scenario.createMany(maxSize);
    }

}
