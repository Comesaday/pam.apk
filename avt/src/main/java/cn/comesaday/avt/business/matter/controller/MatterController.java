package cn.comesaday.avt.business.matter.controller;

import cn.comesaday.avt.business.matter.model.Matter;
import cn.comesaday.avt.business.matter.service.MatterService;
import cn.comesaday.avt.business.matter.vo.MatterSettingRequest;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import cn.comesaday.coe.core.basic.bean.result.Result;
import org.activiti.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <描述> MasterController
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-18 16:09
 */
@Controller
@RequestMapping("/matter")
public class MatterController {

    // 日志引入
    private final static Logger logger = LoggerFactory.getLogger(MatterController.class);

    @Autowired
    private MatterService matterService;

    /**
     * <说明> 事项表单配置
     * @param settingVo MatterSettingVo
     * @author ChenWei
     * @date 2021/3/19 10:05
     * @return java.lang.String
     */
    @RequestMapping("/setting")
    public String setting(MatterSettingRequest settingVo) {
//        matterService.setting(settingVo);
        return "redirect:/matter/index";
    }

    /**
     * <说明> 移除事项表单配置
     * @param settingVo MatterSettingVo
     * @author ChenWei
     * @date 2021/3/19 10:05
     * @return java.lang.String
     */
    @RequestMapping("/removeSetting")
    public String removeSetting(MatterSettingRequest settingVo) {
        matterService.removeSetting(settingVo);
        return "redirect:/matter/index";
    }

    /**
     * <说明> 事项主页面
     * @param model Model
     * @author ChenWei
     * @date 2021/3/18 13:55
     * @return java.lang.String
     */
    @RequestMapping("/index")
    public String list(Model model) {
        Matter matter = new Matter();
        List<Matter> matters = matterService.findAll(matter);
        model.addAttribute("matters", matters);
        return "matter/index";
    }

    /**
     * <说明> 事项编辑页面
     * @param model Model
     * @param matterId 事项ID
     * @author ChenWei
     * @date 2021/3/18 18:47
     * @return java.lang.String
     */
    @RequestMapping("/edit/{matterId}")
    public String edit(Model model, @PathVariable(value = "matterId") Long matterId) {
        Matter matter = null;
        if (null != matterId) {
            matter = matterService.findOne(matterId);
        } else {
            matter = new Matter();
        }
        model.addAttribute("matter", matter);
        return "matter/edit";
    }

    /**
     * <说明> 保存事项
     * @param matter Matter
     * @author ChenWei
     * @date 2021/3/18 18:53
     * @return java.lang.String
     */
    public String save(Matter matter) {
        matterService.save(matter);
        return "redirect:/matter/index";
    }

    /**
     * <说明> 事项创建流程
     * @param matterId 事项ID
     * @author ChenWei
     * @date 2021/3/16 15:14
     * @return JsonResult
     */
    @RequestMapping("/create/process/{matterId}")
    @ResponseBody
    public JsonResult create(@PathVariable(name = "matterId") Long matterId) {
        try {
            org.activiti.engine.repository.Model model = matterService.createMatterProcess(matterId);
            String url = "/static/modeler.html?modelId=" + model.getId();
            return Result.success("流程创建成功", url);
        } catch (Exception e) {
            logger.error("流程创建失败：{}", e.getMessage(), e);
            return Result.fail("流程创建失败：" + e);
        }
    }

    /**
     * <说明> 事项流程部署
     * @param matterId 事项id
     * @author ChenWei
     * @date 2021/3/8 17:55
     * @return JsonResult
     */
    @RequestMapping("/deploy/process/{matterId}")
    @ResponseBody
    public JsonResult deploy(@PathVariable(name = "matterId") Long matterId) {
        try {
            Deployment deploy = matterService.deployMatterProcess(matterId);
            return Result.success("流程部署成功", deploy);
        } catch (Exception e) {
            logger.error("流程部署失败：{}", e.getMessage(), e);
            return Result.fail("流程部署失败：" + e);
        }
    }

}
