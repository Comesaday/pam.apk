package cn.comesaday.avt.business.matter.vo;

import java.util.List;

/**
 * <描述> MatterSettingVo
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-19 10:02
 */
public class MatterSettingVo {

    // 事项ID
    private Long matterId;

    // 事项元素ids
    private List<Long> elementIds;

    public Long getMatterId() {
        return matterId;
    }

    public void setMatterId(Long matterId) {
        this.matterId = matterId;
    }

    public List<Long> getElementIds() {
        return elementIds;
    }

    public void setElementIds(List<Long> elementIds) {
        this.elementIds = elementIds;
    }
}
