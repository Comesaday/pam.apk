package cn.comesaday.avt.work.process.controller;

import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <描述> 流程定义and流程部署
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-08 17:02
 */

@Controller
@RequestMapping("/process")
public class ProcessController {

    // 日志引入
    private final static Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @Autowired
    private RepositoryService repositoryService;

    public JsonResult create() {
        JsonResult result = new JsonResult();
        try {
            Model model = repositoryService.newModel();
            result.setSuccess("流程创建成功");
        } catch (Exception e) {
            result.setError("流程创建异常：" + e);
        }
        return result;
    }

    /**
     * <说明> 流程部署
     * @param modelId 流程模型ID
     * @author ChenWei
     * @date 2021/3/8 17:55
     * @return JsonResult
     */
    @RequestMapping("/defined/{modelId}")
    public JsonResult defined(@PathVariable(name = "modelId") String modelId) {
        JsonResult result = new JsonResult();
        try {
            Model model = repositoryService.getModel(modelId);
            byte[] bytes = repositoryService.getModelEditorSource(model.getId());
            if (null == bytes) {
                return result.setError("模型数据为空");
            }
            JsonNode jsonNode = new ObjectMapper().readTree(bytes);
            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
            if (bpmnModel.getProcesses().size() == NumConstant.I0) {
                return result.setError("缺少流程主线");
            }
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
            String processName = model.getName() + ".bpmn20.xml";
            Deployment deploy = repositoryService.createDeployment().name(processName)
                    .addString(processName, new String(bpmnBytes, "utf-8")).deploy();
            model.setDeploymentId(deploy.getId());
            repositoryService.saveModel(model);
            result.setSuccess("流程部署成功");
        } catch (Exception e) {
            logger.error("流程部署异常：{}", e.getMessage(), e);
            result.setError("流程部署异常：" + e);
        }
        return result;
    }
}
