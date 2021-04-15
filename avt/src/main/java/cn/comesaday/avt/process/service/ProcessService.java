package cn.comesaday.avt.process.service;

import cn.comesaday.avt.matter.enums.MatterEnum;
import cn.comesaday.avt.matter.model.Matter;
import cn.comesaday.avt.matter.service.MatterService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <描述> ProcessService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-09 14:05
 */
@Service
@Transactional
public class ProcessService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private MatterService matterService;

    /**
     * <说明> 创建流程模型
     * @param matterId 事项ID
     * @author ChenWei
     * @date 2021/3/16 14:12
     * @return Model
     */
    public Model createModel(Long matterId) throws Exception {
        // 事项是否创建
        Matter matter = matterService.getBasicMatter(matterId);
        matterService.checkMatterConfig(matter, MatterEnum.DEFINED.getStatus(), Boolean.TRUE);
        // 创建保存流程model
        Model model = this.createNewModel(matter);
        matter.setModelId(model.getId());
        matter.setStatus(MatterEnum.DEFINED.getStatus());
        matterService.save(matter);
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
     * <说明> 模型信息初始化
     * @param matter Matter
     * @author ChenWei
     * @date 2021/3/16 14:13
     * @return Model
     */
    public Model createNewModel(Matter matter) {
        Model model = repositoryService.newModel();
        model.setName(matter.getName());
        model.setKey(matter.getCode());
        model.setCategory(matter.getCode());
        model.setVersion(NumConstant.I1);
        ObjectNode modelNode = new ObjectMapper().createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, matter.getName());
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, matter.getRemark());
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, model.getVersion());
        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);
        return model;
    }

    /**
     * <说明> 流程部署
     * @param matterId 模型id
     * @author ChenWei
     * @date 2021/3/16 14:21
     * @return Deployment
     */
    public Deployment deploymentModel(Long matterId) throws Exception {
        Matter matter = matterService.getBasicMatter(matterId);
        // 检查事项状态
        matterService.checkMatterConfig(matter, MatterEnum.DEPLOY.getStatus(), Boolean.TRUE);
        // 流程部署
        Model model = repositoryService.getModel(matter.getModelId());
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

        matter.setDeployId(deploy.getId());
        matter.setStatus(MatterEnum.DEPLOY.getStatus());
        matterService.save(matter);
        return deploy;
    }
}
