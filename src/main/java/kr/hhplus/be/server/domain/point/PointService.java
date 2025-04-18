package kr.hhplus.be.server.domain.point;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    @Transactional(readOnly = true)
    public Optional<Point> findPoint(Long userId) {
        return pointRepository.findPointByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<PointHistory> findPointHistories(Long userId) {
        return pointRepository.findPointHistoriesByUserId(userId);
    }

    @Transactional
    public Point increase(PointCommand.Increase command) {
        Point point = findPoint(command.getUserId()).orElseThrow();

        Long amount = command.getAmount();
        point.increase(amount);
        pointRepository.savePoint(point);

        PointHistory history = PointHistory.builder()
                .userId(point.getUserId())
                .amount(amount)
                .originType(command.getOriginType())
                .build();
        pointRepository.savePointHistory(history);

        return point;
    }

    @Transactional
    public Point decrease(PointCommand.Decrease command) {
        Point point = findPoint(command.getUserId()).orElseThrow();

        Long amount = command.getAmount();
        point.decrease(amount);
        pointRepository.savePoint(point);

        PointHistory history = PointHistory.builder()
                .userId(point.getUserId())
                .amount(amount)
                .originType(command.getOriginType())
                .build();
        pointRepository.savePointHistory(history);

        return point;
    }

}
