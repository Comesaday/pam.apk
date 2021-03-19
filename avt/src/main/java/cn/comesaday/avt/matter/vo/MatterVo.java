package cn.comesaday.avt.matter.vo;

import cn.comesaday.avt.matter.model.Matter;
import cn.comesaday.avt.matter.model.MatterSetting;

import java.util.List;

/**
 * <描述> MatterVo
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-18 17:10
 */
public class MatterVo {

    // 事项
    private Matter matter;

    // 事项表单元素
    private List<MatterSetting> elements;

    public Matter getMatter() {
        return matter;
    }

    public void setMatter(Matter matter) {
        this.matter = matter;
    }

    public List<MatterSetting> getElements() {
        return elements;
    }

    public void setElements(List<MatterSetting> elements) {
        this.elements = elements;
    }
}
