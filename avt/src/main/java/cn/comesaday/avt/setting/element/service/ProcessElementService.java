package cn.comesaday.avt.setting.element.service;

import cn.comesaday.avt.setting.element.model.ProcessElement;

import java.util.List;

/**
 * <描述> ProcessElementService
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-09 13:34
 */
public interface ProcessElementService {

    ProcessElement queryByCondition(ProcessElement element);

    List<ProcessElement> queryAllByCondition(ProcessElement element);

    void save(ProcessElement element);
}
