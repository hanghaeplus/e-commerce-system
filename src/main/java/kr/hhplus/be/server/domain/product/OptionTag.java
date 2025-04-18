package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.AuditableEntity;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
        name = "product_option_tag",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_option_id", "code"})
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionTag extends AuditableEntity {

    /**
     * 아이디
     */
    @Id
    @Column(name = "product_option_tag_id", nullable = false, updatable = false)
    private Long id;

    /**
     * 상품 옵션 아이디
     */
    @Column(name = "product_option_id", nullable = false, updatable = false)
    private Long productOptionId;

    /**
     * 태그 코드
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false)
    private Tag code;

    /**
     * 태그 이름
     */
    @Column(name = "name", nullable = false)
    private String name;

}
