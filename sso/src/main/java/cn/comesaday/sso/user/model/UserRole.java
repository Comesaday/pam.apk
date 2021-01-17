package cn.comesaday.sso.user.model;

import cn.comesaday.coe.core.basic.model.IdEntity;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-29 20:15
 */
@Table(name = "sys_user_role")
@Entity
public class UserRole extends IdEntity {

    @Column
    private Long userId;

    @Column
    @Length(min = 0, max = 20)
    private String roleCode;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
