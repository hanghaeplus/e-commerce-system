package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
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
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends AuditableEntity {

    /**
     * 아이디
     */
    @Id
    @Column(name = "product_id", nullable = false, updatable = false)
    private Long id;

    /**
     * 상품 이름
     */
    @Column(name = "name", nullable = false)
    private String name;

    // -------------------------------------------------------------------------------------------------

    /**
     * 옵션 목록
     */
    @Transient
    private List<Option> options = Collections.emptyList();

    public void addOption(Option option) {
        List<Option> options = Objects.requireNonNullElseGet(this.options, Collections::emptyList);
        this.options = Stream.concat(options.stream(), Stream.of(option)).toList();
    }

}
