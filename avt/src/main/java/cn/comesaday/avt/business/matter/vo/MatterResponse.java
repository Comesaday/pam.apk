package cn.comesaday.avt.business.matter.vo;

import cn.comesaday.avt.business.matter.model.Matter;
import cn.comesaday.avt.business.matter.model.MatterFieldSetting;

import java.util.List;

/**
 * <描述> MatterVo
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-18 17:10
 */
public class MatterResponse {

    // 事项
    private Matter matter;

    // 事项表单元素
    private List<MatterFieldSetting> elements;

    public Matter getMatter() {
        return matter;
    }

    public void setMatter(Matter matter) {
        this.matter = matter;
    }

    public List<MatterFieldSetting> getElements() {
        return elements;
    }

    public void setElements(List<MatterFieldSetting> elements) {
        this.elements = elements;
    }
}
