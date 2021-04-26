package cn.comesaday.avt.business.apply.controller;

import cn.comesaday.avt.business.apply.service.ApplyService;
import cn.comesaday.avt.business.apply.vo.UserApplyRequest;
import cn.comesaday.avt.business.matter.service.MatterService;
import cn.comesaday.avt.business.matter.vo.MatterResponse;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
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
     * @param userApplyRequest 申请信息
     * @author ChenWei
     * @date 2021/4/1 17:28
     * @return java.lang.String
     */
    @RequestMapping("/submit/info")
    public String create(Model model, UserApplyRequest userApplyRequest) {
        try {
            JsonResult result = applyService.apply(userApplyRequest);
            model.addAttribute("result", result);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "apply/apply-view";
    }

    /**
     * <说明> 查看申请信息
     * @param model Model
     * @param askInfoId 申请id
     * @author ChenWei
     * @date 2021/4/1 17:34
     * @return java.lang.String
     */
    @RequestMapping("/view/{askInfoId}")
    public String view(Model model, @PathVariable(value = "askInfoId") Long askInfoId) {
        try {
            UserApplyRequest askInfo = applyService.queryDetail(askInfoId);
            model.addAttribute("askInfo", askInfo);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "apply/apply-view";
    }
}
