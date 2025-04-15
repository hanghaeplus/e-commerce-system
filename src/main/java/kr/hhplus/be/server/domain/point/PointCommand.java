package kr.hhplus.be.server.domain.point;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PointCommand {

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Point {

        private final Long userId;

    }

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class History {

        private final Long userId;

    }

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Increase {

        private final Long userId;
        private final Long amount;
        private final PointHistory.OriginType originType;

    }

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Decrease {

        private final Long userId;
        private final Long amount;
        private final PointHistory.OriginType originType;

    }

}
