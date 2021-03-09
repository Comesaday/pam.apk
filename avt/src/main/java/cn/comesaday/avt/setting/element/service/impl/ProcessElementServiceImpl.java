package cn.comesaday.avt.setting.element.service.impl;

import cn.comesaday.avt.setting.element.manager.ProcessElementManager;
import cn.comesaday.avt.setting.element.model.ProcessElement;
import cn.comesaday.avt.setting.element.service.ProcessElementService;
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
public class ProcessElementServiceImpl implements ProcessElementService {

    @Autowired
    private ProcessElementManager processElementManager;

    /**
     * <说明> 按条件查询单个
     * @param element ProcessElement
     * @author ChenWei
     * @date 2021/3/9 13:46
     * @return ProcessElement
     */
    @Override
    public ProcessElement queryByCondition(ProcessElement element) {
        Example<ProcessElement> example = Example.of(element);
        List<ProcessElement> elements = processElementManager.findAll(example);
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
    public List<ProcessElement> queryAllByCondition(ProcessElement element) {
        Example<ProcessElement> example = Example.of(element);
        return processElementManager.findAll(example);
    }

    /**
     * <说明> 保存
     * @param element ProcessElement
     * @author ChenWei
     * @date 2021/3/9 13:46
     */
    @Override
    public void save(ProcessElement element) {
        processElementManager.save(element);
    }
}
