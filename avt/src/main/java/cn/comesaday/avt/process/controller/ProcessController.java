package cn.comesaday.avt.process.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;

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

    /**
     * <说明> 流程定义
     * @param processId 流程ID
     * @author ChenWei
     * @date 2021/3/8 17:55
     * @return JsonResult
     */
    @RequestMapping("/defined")
    public JsonResult defined(String processId) {
        JsonResult result = new JsonResult();
        try {
            Model model = repositoryService.getModel(processId);
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
            String processName = model.getName() + ".xml";
            Deployment deploy = repositoryService.createDeployment().name(processName)
                    .addString(processName, new String(bpmnBytes, "utf-8")).deploy();
            model.setDeploymentId(deploy.getId());
            repositoryService.saveModel(model);
        } catch (IOException e) {
            logger.error("流程定义异常：{}", e.getMessage(), e);
        }
        return result.setSuccess("流程定义成功");
    }
}
