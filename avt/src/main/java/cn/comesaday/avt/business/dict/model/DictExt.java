package cn.comesaday.avt.business.dict.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * <描述> 数据字典扩展
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-16 10:55
 */
@Entity
@Table(name = "AVT_DICT_EXT")
public class DictExt extends IdEntity {

    private Long extLong;

    private Integer extInt;

    private String extStr;

    private Float extFloat;

    private Double extDouble;

    private BigDecimal extBig;

    // 扩展字段备注
    private String remark;

    // 扩展字段标识
    private String flag;

    public Long getExtLong() {
        return extLong;
    }

    public void setExtLong(Long extLong) {
        this.extLong = extLong;
    }

    public Integer getExtInt() {
        return extInt;
    }

    public void setExtInt(Integer extInt) {
        this.extInt = extInt;
    }

    public String getExtStr() {
        return extStr;
    }

    public void setExtStr(String extStr) {
        this.extStr = extStr;
    }

    public Float getExtFloat() {
        return extFloat;
    }

    public void setExtFloat(Float extFloat) {
        this.extFloat = extFloat;
    }

    public Double getExtDouble() {
        return extDouble;
    }

    public void setExtDouble(Double extDouble) {
        this.extDouble = extDouble;
    }

    public BigDecimal getExtBig() {
        return extBig;
    }

    public void setExtBig(BigDecimal extBig) {
        this.extBig = extBig;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
