package cn.comesaday.avt.demo.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <Descripe>
 *
 * @author: ChenWei
 * @CreateAt: 2020-08-10 15:02
 */
@Entity
@Table(name = "platform_demo")
public class Demo extends IdEntity {

    @Column
    private String name;

    @Column
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
