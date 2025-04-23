package kr.hhplus.be.server.test.fixture;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @param <S> fixture scenario
 * @param <F> test fixture
 */
public interface FixtureScenario<S extends Enum<? extends S>, F> {

    F createOne();

    default List<F> createMany(int maxSize) {
        int size = new Random().nextInt(1, maxSize + 1);
        return IntStream.range(0, size).mapToObj(i -> createOne()).toList();
    }

}
