package kr.hhplus.be.server.configuration.jpa.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kr.hhplus.be.server.domain.common.CodeAware;

@Converter
interface CodeAwareConverter<E extends Enum<E> & CodeAware> extends AttributeConverter<E, Integer> {

    @Override
    default Integer convertToDatabaseColumn(E attribute) {
        return attribute.getCode();
    }

}
