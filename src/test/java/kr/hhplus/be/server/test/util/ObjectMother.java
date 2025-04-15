package kr.hhplus.be.server.test.util;

import java.util.List;
import java.util.Random;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FailoverIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;

import lombok.Getter;

public class ObjectMother {

    @Getter
    private static final FixtureMonkey fixtureMonkey;

    private static final Random random = new Random();

    static {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(new FailoverIntrospector(List.of(
                        FieldReflectionArbitraryIntrospector.INSTANCE,
                        BuilderArbitraryIntrospector.INSTANCE
                )))
                .defaultNotNull(true)
                .build();
    }

    public static long getPositiveLong() {
        return getLong(1L, Long.MAX_VALUE);
    }

    public static long getPositiveOrZeroLong() {
        return getLong(0L, Long.MAX_VALUE);
    }

    public static long getNegativeLong() {
        return getLong(Long.MIN_VALUE, 0L);
    }

    public static long getNegativeOrZeroLong() {
        return getLong(Long.MIN_VALUE, 1L);
    }

    public static long getLong(long start, long end) {
        return random.nextLong(start, end);
    }

}
