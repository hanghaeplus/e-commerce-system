package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.BusinessError;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.domain.common.AuditableEntity;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "product_option")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Option extends AuditableEntity {

    /**
     * 아이디
     */
    @Id
    @Column(name = "product_option_id", nullable = false, updatable = false)
    private Long id;

    /**
     * 상품 아이디
     */
    @Column(name = "product_id", nullable = false, updatable = false)
    private Long productId;

    /**
     * 상품 옵션 이름
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 가격
     */
    @Column(name = "price", nullable = false)
    private Integer price;

    // -------------------------------------------------------------------------------------------------

    /**
     * 태그 목록
     */
    @Transient
    private List<OptionTag> tags = Collections.emptyList();

    public void addTag(OptionTag tag) {
        List<OptionTag> tags = Objects.requireNonNullElseGet(this.tags, Collections::emptyList);

        if (tags.stream().map(OptionTag::getCode).anyMatch(it -> it == tag.getCode())) {
            throw new BusinessException(BusinessError.OPTION_DUPLICATED_TAG);
        }

        this.tags = Stream.concat(tags.stream(), Stream.of(tag)).toList();
    }

}
