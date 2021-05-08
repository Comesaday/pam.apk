package cn.comesaday.avt.business.apply.controller;

import cn.comesaday.avt.business.apply.service.ApplyService;
import cn.comesaday.avt.business.apply.vo.DynamicApplyInfo;
import cn.comesaday.avt.business.apply.vo.StaticApplyInfo;
import cn.comesaday.avt.business.apply.vo.UserApply;
import cn.comesaday.avt.business.matter.service.MatterService;
import cn.comesaday.avt.business.matter.vo.MatterResponse;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import cn.comesaday.coe.core.basic.exception.PamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <描述> 请假controller
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-08 16:10
 */
@Controller
@RequestMapping("/apply")
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private MatterService matterService;


    /**
     * <说明> 事项申请页面
     * @param model Model
     * @param matterId 事项id
     * @author ChenWei
     * @date 2021/4/1 17:28
     * @return java.lang.String
     */
    @RequestMapping("/matter/{matterId}")
    public String apply(Model model, @PathVariable(name = "matterId") Long matterId) {
        try {
            MatterResponse matterInfo = matterService.getMatterInfo(matterId);
            model.addAttribute("matterInfo", matterInfo);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "apply/apply-edit";
    }


    /**
     * <说明> 保存申请信息
     * @param model Model
     * @param userApply 申请信息
     * @author ChenWei
     * @date 2021/4/1 17:28
     * @return java.lang.String
     */
    @RequestMapping("/submit/info")
    public String create(Model model, UserApply userApply) {
        try {
            JsonResult result = applyService.apply(userApply);
            model.addAttribute("result", result);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "apply/apply-view";
    }


    /**
     * <说明> 查看申请信息
     * @param model Model
     * @param askId 申请id
     * @author ChenWei
     * @date 2021/4/1 17:34
     * @return java.lang.String
     */
    @RequestMapping("/query/basic/{askId}")
    public String getBasicInfo(Model model, @PathVariable(value = "askId") Long askId) {
        try {
            UserApply askInfo = applyService.getBasic(askId);
            model.addAttribute("askInfo", askInfo);
        } catch (PamException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "apply/apply-view";
    }


    /**
     * <说明> 获取静态申请信息
     * @param model Model
     * @param askId 申请id
     * @author ChenWei
     * @date 2021/5/8 9:39
     * @return java.lang.String
     */
    @RequestMapping("/query/static/{askId}")
    public String getStaticInfo(Model model, @PathVariable(value = "askId") Long askId) {
        try {
            StaticApplyInfo staticApplyInfo = applyService.getStatic(askId);
            model.addAttribute("staticApplyInfo", staticApplyInfo);
        } catch (PamException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "apply/apply-view";
    }


    /**
     * <说明> 获取动态申请信息
     * @param model Model
     * @param askId Long
     * @author ChenWei
     * @date 2021/5/8 9:39
     * @return java.lang.String
     */
    @RequestMapping("/query/dynamic/{askId}")
    public String getDynamicInfo(Model model, @PathVariable(value = "askId") Long askId) {
        try {
            DynamicApplyInfo dynamicApplyInfo = applyService.getDynamic(askId);
            model.addAttribute("dynamicApplyInfo", dynamicApplyInfo);
        } catch (PamException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "apply/apply-view";
    }
}
