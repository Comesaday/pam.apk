package cn.comesaday.avt.matter.controller;

import cn.comesaday.avt.matter.model.Matter;
import cn.comesaday.avt.matter.service.MatterService;
import cn.comesaday.avt.matter.vo.MatterSettingVo;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
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
    public String setting(MatterSettingVo settingVo) {
        matterService.setting(settingVo);
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
    public String removeSetting(MatterSettingVo settingVo) {
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

    @ResponseBody
    @RequestMapping("/test")
    public JsonResult test() {
        JsonResult result = new JsonResult();
        List<Matter> matters = matterService.findAllByProperty("isDeleted", false);
        List<Matter> allLike = matterService.findAllLike("code", "ASK");
        result.setData(matters);
        return result;
    }
}
