package cn.comesaday.avt.setting.dict.service.impl;

import cn.comesaday.avt.setting.dict.manager.DictExtManager;
import cn.comesaday.avt.setting.dict.model.DictExt;
import cn.comesaday.avt.setting.dict.service.DictExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <描述> DictExtServiceImpl
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-16 11:36
 */
@Service
public class DictExtServiceImpl implements DictExtService {

    @Autowired
    private DictExtManager dictExtManager;

    @Override
    public DictExt findById(Long extId) {
        return dictExtManager.getOne(extId);
    }

    @Override
    public DictExt createModelExt(String modelId) {
        DictExt dictExt = new DictExt();
        dictExt.setExtStr(modelId);
        dictExt.setRemark("流程模型ID");
        return dictExtManager.save(dictExt);
    }
}
