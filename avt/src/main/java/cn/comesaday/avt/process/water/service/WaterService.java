package cn.comesaday.avt.process.water.service;

import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import cn.comesaday.avt.process.water.model.Water;
import cn.comesaday.coe.common.util.JsonUtil;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <描述> WaterService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-09 14:05
 */
@Service
@Transactional
public class WaterService extends BaseService<Water, Long> {

    /**
     * <说明> 记录流程成功信息
     * @param water Water
     * @param message String
     * @author ChenWei
     * @date 2021/4/23 15:18
     * @return cn.comesaday.avt.process.water.model.Water
     */
    public Water saveSuccess(Water water, ProcessVariable variable, String message) {
        water.setResult(message);
        water.setSuccess(Boolean.TRUE);
        water.setParam(JsonUtil.toJson(variable));
        return this.save(water);
    }

    /**
     * <说明> 记录流程失败信息
     * @param water Water
     * @param message String
     * @author ChenWei
     * @date 2021/4/23 15:18
     * @return cn.comesaday.avt.process.water.model.Water
     */
    public Water saveFail(Water water, ProcessVariable variable, String message) {
        water.setResult(message);
        water.setSuccess(Boolean.FALSE);
        water.setParam(JsonUtil.toJson(variable));
        return this.save(water);
    }
}
