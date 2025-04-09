package kr.hhplus.be.server.domain.point;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointRepository {

    Optional<Point> findPointByUserId(Long userId);

    Point savePoint(Point point);

    List<PointHistory> findPointHistoriesByUserId(Long userId);

    PointHistory savePointHistory(PointHistory pointHistory);

}
