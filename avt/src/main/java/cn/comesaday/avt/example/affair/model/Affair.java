package cn.comesaday.avt.example.affair.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * <描述> Example
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-11 14:03
 */
@Entity
@Table(name = "affair")
public class Affair extends IdEntity {

    private String name;

    private Integer age;

    private Long time;

    @Version
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
