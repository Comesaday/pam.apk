package cn.comesaday.avt.process.model.service;

import cn.comesaday.avt.business.matter.service.MatterService;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.exception.PamException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <描述> ModelService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-19 16:53
 */
public class ModelService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private MatterService matterService;

    /**
     * <说明> 创建流程模型
     * @param key 模型code
     * @param name 模型名称
     * @param description 事项
     * @param version 模型版本
     * @author ChenWei
     * @date 2021/3/16 14:12
     * @return Model
     */
    public Model createModel(String key, String name, String description, Integer version) throws Exception {
        // 创建保存流程model
        Model model = repositoryService.newModel();
        model.setName(name);
        model.setKey(key);
        model.setCategory(key);
        model.setVersion(version);
        ObjectNode modelNode = new ObjectMapper().createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, version);
        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);
        //完善ModelEditorSource
        ObjectNode editorNode = new ObjectMapper().createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = new ObjectMapper().createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(model.getId(), editorNode.toString()
                .getBytes("utf-8"));
        return model;
    }


    /**
     * <说明> 流程部署
     * @param modelId 事项模型id
     * @author ChenWei
     * @date 2021/3/16 14:21
     * @return Deployment
     */
    public Deployment deploymentModel(String modelId) throws Exception {
        // 流程部署
        Model model = repositoryService.getModel(modelId);
        byte[] bytes = repositoryService.getModelEditorSource(model.getId());
        if (null == bytes) {
            throw new PamException("模型数据为空");
        }
        JsonNode jsonNode = new ObjectMapper().readTree(bytes);
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
        if (bpmnModel.getProcesses().size() == NumConstant.I0) {
            throw new PamException("缺少流程主线");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        String processName = model.getName() + ".bpmn20.xml";
        Deployment deploy = repositoryService.createDeployment().name(processName)
                .addString(processName, new String(bpmnBytes, "utf-8")).deploy();
        // 保存部署信息
        model.setDeploymentId(deploy.getId());
        repositoryService.saveModel(model);
        return deploy;
    }
}
