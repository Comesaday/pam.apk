package cn.comesaday.avt.process.controller;

import cn.comesaday.avt.setting.element.model.ProcessElement;
import cn.comesaday.avt.setting.element.service.ProcessElementService;
import cn.comesaday.avt.process.service.PCreateService;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <描述> 流程部署
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-08 17:02
 */

@RestController
@RequestMapping("/process")
public class ProcessController {

    // 日志引入
    private final static Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessElementService processElementService;

    @Autowired
    private PCreateService pCreateService;

    @RequestMapping("/create/{categoryCode}")
    public JsonResult create(@PathVariable(name = "categoryCode") String categoryCode) {
        JsonResult result = new JsonResult();
        try {
            ProcessElement element = new ProcessElement();
            element.setCode(categoryCode);
            List<ProcessElement> elements = processElementService.queryAllByCondition(element);
            if (CollectionUtils.isEmpty(elements)) {
                return result.setError("未创建的流程类别");
            }
            element = elements.get(NumConstant.I0);
            Model model = repositoryService.getModel(element.getExtField());
            if (null != model) {
                return result.setError("已存在对应流程");
            }
            model = pCreateService.createNewModel(element);
            element.setExtField(model.getId());
            processElementService.save(element);
            result.setSuccess("流程创建成功");
            //完善ModelEditorSource
            ObjectNode editorNode = new ObjectMapper().createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = new ObjectMapper().createObjectNode();
            stencilSetNode.put("namespace",
                    "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));
            Map<String, Object> data = new HashMap<>();
            data.put("url", "/static/modeler.html?modelId=" + model.getId());
            result.setSuccess("流程创建成功", data);
        } catch (Exception e) {
            result.setError("流程创建失败：" + e);
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
    @RequestMapping("/define/{modelId}")
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
            result.setError("流程部署失败：" + e);
        }
        return result;
    }
}
