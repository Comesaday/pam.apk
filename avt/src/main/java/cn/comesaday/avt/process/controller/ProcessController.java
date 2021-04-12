package cn.comesaday.avt.process.controller;

import cn.comesaday.avt.process.service.ProcessService;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <描述> 流程控制
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
    private ProcessService processService;

    /**
     * <说明> 创建流程
     * @param matterId 事项ID
     * @author ChenWei
     * @date 2021/3/16 15:14
     * @return JsonResult
     */
    @RequestMapping("/create/{matterId}")
    public JsonResult create(@PathVariable(name = "matterId") Long matterId) {
        JsonResult result = new JsonResult();
        try {
            Model model = processService.createModel(matterId);
            String url = "/static/modeler.html?modelId=" + model.getId();
            result.setSuccess("流程创建成功", url);
        } catch (Exception e) {
            logger.error("流程创建失败：{}", e.getMessage(), e);
            result.setError("流程创建失败：" + e);
        }
        return result;
    }

    /**
     * <说明> 流程部署
     * @param matterId 事项id
     * @author ChenWei
     * @date 2021/3/8 17:55
     * @return JsonResult
     */
    @RequestMapping("/deploy/{matterId}")
    public JsonResult deploy(@PathVariable(name = "matterId") Long matterId) {
        JsonResult result = new JsonResult();
        try {
            Deployment deploy = processService.deploymentModel(matterId);
            result.setSuccess("流程部署成功", deploy);
        } catch (Exception e) {
            logger.error("流程部署失败：{}", e.getMessage(), e);
            result.setError("流程部署失败：" + e);
        }
        return result;
    }
}
