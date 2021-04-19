package cn.comesaday.avt.business.user.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> UserGroup
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-12 13:55
 */
@Entity
@Table(name = "AVT_USER_GROUP")
public class UserGroup extends IdEntity {

    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
