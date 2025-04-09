package kr.hhplus.be.server.domain.point.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PointValidators {

    private final List<PointValidator> validators;

    public void validate(PointValidationContext context) {
        validators.stream()
                .filter(validator -> validator.supports(context))
                .findFirst()
                .ifPresent(validator -> validator.validate(context));
    }

}
