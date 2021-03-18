package cn.comesaday.avt.setting.dict.service.impl;

import cn.comesaday.avt.setting.dict.manager.DictManager;
import cn.comesaday.avt.setting.dict.model.Dict;
import cn.comesaday.avt.setting.dict.service.DictService;
import cn.comesaday.coe.common.constant.NumConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <描述> ProcessElementServiceImpl
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-09 13:34
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictManager dictManager;

    /**
     * <说明> 按条件查询单个
     * @param element ProcessElement
     * @author ChenWei
     * @date 2021/3/9 13:46
     * @return ProcessElement
     */
    @Override
    public Dict findOne(Dict element) {
        Example<Dict> example = Example.of(element);
        List<Dict> elements = dictManager.findAll(example);
        if (CollectionUtils.isEmpty(elements)) {
            return elements.get(NumConstant.I0);
        }
        return null;
    }

    /**
     * <说明> 按条件查询所有
     * @param element ProcessElement
     * @author ChenWei
     * @date 2021/3/9 13:46
     * @return List<ProcessElement>
     */
    @Override
    public List<Dict> findAll(Dict element) {
        Example<Dict> example = Example.of(element);
        return dictManager.findAll(example);
    }

    /**
     * <说明> 保存
     * @param element ProcessElement
     * @author ChenWei
     * @date 2021/3/9 13:46
     */
    @Override
    public Dict saveAndUpdate(Dict element) {
        return dictManager.save(element);
    }

    @Override
    public Dict findOne(Long id) {
        return dictManager.findById(id).orElse(new Dict());
    }

    @Override
    public void delete(Long dictId) {
        dictManager.deleteById(dictId);
    }

    @Override
    public List<Dict> listProcess(String pmCode) {
        return dictManager.listProcess(pmCode);
    }
}
