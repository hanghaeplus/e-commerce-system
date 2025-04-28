package kr.hhplus.be.server.test.fixture.user;

import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.test.fixture.FixtureScenario;
import kr.hhplus.be.server.test.fixture.Fixtures;

import java.util.List;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static net.jqwik.api.Arbitraries.longs;

public enum UserScenario implements FixtureScenario<UserScenario, User> {

    VALID {
        @Override
        public User createOne() {
            return Fixtures.getFixtureMonkey()
                    .giveMeBuilder(User.class)
                    .set(javaGetter(User::getId), longs().greaterOrEqual(1))
                    .set(javaGetter(User::isEnabled), true)
                    .build()
                    .sample();
        }

        @Override
        public List<User> createMany(int maxSize) {
            return Fixtures.getFixtureMonkey()
                    .giveMeBuilder(User.class)
                    .set(javaGetter(User::getId), longs().greaterOrEqual(1))
                    .set(javaGetter(User::isEnabled), true)
                    .build()
                    .list()
                    .ofMaxSize(maxSize)
                    .sample();
        }
    },

}
