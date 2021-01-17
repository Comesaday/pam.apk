package cn.comesaday.sso.role.model;

import cn.comesaday.coe.core.basic.model.IdEntity;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-29 20:13
 */
@Entity
@Table(name = "sys_role")
public class Role extends IdEntity {

    @Column
    @Length(min = 0, max = 20)
    private String name;

    @Column
    @Length(min = 0, max = 20)
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
