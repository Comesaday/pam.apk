package cn.comesaday.avt.process.service;

import cn.comesaday.avt.setting.element.model.ProcessElement;
import org.activiti.engine.repository.Model;

/**
 * <描述> PCreateService
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-09 17:06
 */
public interface PCreateService {

    Model createNewModel(ProcessElement element);
}
