package kr.hhplus.be.server.domain.point;

import kr.hhplus.be.server.domain.point.validator.PointValidationContext;
import kr.hhplus.be.server.domain.point.validator.PointValidators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final PointValidators pointValidators;

    @Transactional(readOnly = true)
    public Point getPoint(PointCommand.Point command) {
        return pointRepository.findPointByUserId(command.getUserId()).orElseThrow();
    }

    @Transactional
    public Point increase(PointCommand.Increase command) {
        Long userId = command.getUserId();
        Long amount = command.getAmount();
        Point point = pointRepository.findPointByUserId(userId).orElseThrow();

        PointValidationContext context = PointMapper.toIncreaseContext(command, point.getBalance());
        pointValidators.validate(context);

        point.increase(amount);
        pointRepository.savePoint(point);

        PointHistory history = PointHistory.builder()
                .userId(userId)
                .amount(amount)
                .originType(command.getOriginType())
                .build();
        pointRepository.savePointHistory(history);

        return point;
    }

    @Transactional
    public Point decrease(PointCommand.Decrease command) {
        Long userId = command.getUserId();
        Long amount = command.getAmount();
        Point point = pointRepository.findPointByUserId(userId).orElseThrow();

        PointValidationContext context = PointMapper.toDecreaseContext(command, point.getBalance());
        pointValidators.validate(context);

        point.decrease(amount);
        pointRepository.savePoint(point);

        PointHistory history = PointHistory.builder()
                .userId(userId)
                .amount(amount)
                .originType(command.getOriginType())
                .build();
        pointRepository.savePointHistory(history);

        return point;
    }

    @Transactional(readOnly = true)
    public List<PointHistory> getPointHistories(PointCommand.History command) {
        return pointRepository.findPointHistoriesByUserId(command.getUserId());
    }

}
