package cn.comesaday.avt.setting.dict.service;

import cn.comesaday.avt.setting.dict.manager.DictExtManager;
import cn.comesaday.avt.setting.dict.model.DictExt;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <描述> DictExtService
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-16 11:36
 */
@Transactional
@Service
public class DictExtService extends BaseService<DictExt, Long> {

    @Autowired
    private DictExtManager dictExtManager;

    public DictExt createModelExt(String modelId) {
        DictExt dictExt = new DictExt();
        dictExt.setExtStr(modelId);
        dictExt.setRemark("流程模型ID");
        return dictExtManager.save(dictExt);
    }
}
