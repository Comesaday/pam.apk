package cn.comesaday.avt.process.service.impl;

import cn.comesaday.avt.process.service.PCreateService;
import cn.comesaday.avt.setting.element.model.ProcessElement;
import cn.comesaday.coe.common.constant.NumConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <描述> PCreateServiceImpl
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-09 14:05
 */
@Service
public class PCreateServiceImpl implements PCreateService {

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public Model createNewModel(ProcessElement element) {
        Model model = repositoryService.newModel();
        model.setName(element.getName());
        model.setKey(element.getCode());
        model.setCategory(element.getCode());
        model.setVersion(NumConstant.I1);
        ObjectNode modelNode = new ObjectMapper().createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, element.getName());
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, element.getRemark());
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, model.getVersion());
        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);
        return model;
    }
}
