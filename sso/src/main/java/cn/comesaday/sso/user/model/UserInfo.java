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
 * @CreateAt 2020-08-29 19:15
 */
@Entity
@Table(name = "sys_user")
public class UserInfo extends IdEntity {

    @Column
    @Length(min = 0, max = 50)
    private String username;

    @Column
    @Length(min = 0, max = 100)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
