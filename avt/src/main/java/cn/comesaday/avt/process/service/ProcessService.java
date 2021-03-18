package cn.comesaday.avt.process.service;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;

/**
 * <描述> PCreateService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-09 17:06
 */
public interface ProcessService {

    Model createModel(String code) throws Exception;

    Deployment deploymentModel(String modelId) throws Exception;
}
