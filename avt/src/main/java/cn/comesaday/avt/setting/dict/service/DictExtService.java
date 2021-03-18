package cn.comesaday.avt.setting.dict.service;

import cn.comesaday.avt.setting.dict.model.DictExt;

/**
 * <描述> DictExtService
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-16 11:36
 */
public interface DictExtService {

    DictExt findById(Long extId);

    DictExt createModelExt(String modelId);
}
