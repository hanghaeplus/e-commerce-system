package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.BusinessError;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.domain.common.AuditableEntity;
import lombok.*;
import org.hibernate.type.YesNoConverter;

import java.util.*;

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

    /**
     * 기본 가격
     */
    @Column(name = "price", nullable = false)
    private Integer price;

    /**
     * 활성화 여부
     */
    @Getter(AccessLevel.NONE)
    @Convert(converter = YesNoConverter.class)
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    // -------------------------------------------------------------------------------------------------

    /**
     * 옵션 목록
     */
    @Transient
    private List<Option> options = Collections.emptyList();

    // -------------------------------------------------------------------------------------------------

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public boolean isEnabled() {
        if (this.enabled == null) {
            throw new BusinessException(BusinessError.COMMON_NO_INITIALIZED_ENTITY);
        }

        return this.enabled;
    }

    public void addOption(Option option) {
        addOptions(List.of(option));
    }

    public void addOptions(List<Option> options) {
        List<Option> those = new ArrayList<>(this.options);
        those.addAll(options);

        Set<Long> set = new HashSet<>();
        for (Option that : those) {
            Long id = that.getId();
            if (id != null && !set.add(id)) {
                throw new BusinessException(BusinessError.PRODUCT_DUPLICATED_OPTION);
            }
        }

        this.options = List.copyOf(those);
    }

    public int getActualPrice(Long optionId) {
        Option opt = this.options.stream()
                .filter(Objects::nonNull)
                .filter(option -> Objects.equals(option.getId(), optionId))
                .findFirst()
                .orElseThrow();

        return this.price + opt.getAdditionalPrice();
    }

}
