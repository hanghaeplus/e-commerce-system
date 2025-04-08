package kr.hhplus.be.server.domain.point;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hhplus.be.server.domain.point.validator.PointValidationContext;
import kr.hhplus.be.server.domain.point.validator.PointValidators;
import lombok.RequiredArgsConstructor;

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

        PointValidationContext context = PointValidationContext.builder()
                .balance(point.getBalance())
                .amount(amount)
                .actionType(PointValidationContext.ActionType.INCREASE)
                .build();
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

        PointValidationContext context = PointValidationContext.builder()
                .balance(point.getBalance())
                .amount(amount)
                .actionType(PointValidationContext.ActionType.DECREASE)
                .build();
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
