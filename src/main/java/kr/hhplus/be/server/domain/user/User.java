package kr.hhplus.be.server.domain.user;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.BusinessError;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.domain.common.AuditableEntity;
import lombok.*;
import org.hibernate.type.YesNoConverter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditableEntity {

    /**
     * 아이디
     */
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /**
     * 이름
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 비밀번호
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 활성화 여부
     */
    @Getter(AccessLevel.NONE)
    @Convert(converter = YesNoConverter.class)
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

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

}
