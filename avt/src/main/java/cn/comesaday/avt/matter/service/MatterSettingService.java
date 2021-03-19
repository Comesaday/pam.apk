package cn.comesaday.avt.matter.service;

import cn.comesaday.avt.matter.model.MatterSetting;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <描述> MatterSettingService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-18 16:24
 */
@Transactional
@Service
public class MatterSettingService extends BaseService<MatterSetting, Long> {
}
