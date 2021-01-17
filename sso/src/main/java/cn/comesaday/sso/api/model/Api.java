package cn.comesaday.sso.api.model;

import cn.comesaday.coe.core.basic.model.IdEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> api接口表
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-31 15:47
 */
@Entity
@Table(name = "sys_api")
public class Api extends IdEntity {

    /**
     * @Fields  : url地址
     */
    @Column
    @Length(min = 0, max = 200)
    private String url;

    /**
     * @Fields  : 目录名称
     */
    @Column
    @Length(min = 0, max = 250)
    private String name;

    /**
     * @Fields  : 目录名称
     */
    @Column
    @Length(min = 0, max = 250)
    private String apiCode;

    /**
     * @Fields  : 访问权限
     */
    @Column
    @Length(min = 0, max = 1000)
    private String apiPowers;

    /**
     * @Fields  : 请求方式 POST OR GET
     */
    @Column
    @Length(min = 0, max = 20)
    private String method;

    public Api() {
    }

    public Api(String url) {
        this.url = url;
    }

    public Api(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiPowers() {
        return apiPowers;
    }

    public void setApiPowers(String apiPowers) {
        this.apiPowers = apiPowers;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }
}
