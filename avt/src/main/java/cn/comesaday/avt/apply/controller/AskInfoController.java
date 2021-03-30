package cn.comesaday.avt.apply.controller;

import cn.comesaday.avt.apply.model.AskInfo;
import cn.comesaday.avt.apply.service.AskInfoService;
import cn.comesaday.avt.apply.vo.AskInfoVo;
import cn.comesaday.avt.matter.service.MatterService;
import cn.comesaday.avt.matter.vo.MatterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <描述> 请假controller
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-08 16:10
 */
@RestController
@RequestMapping("/ask")
public class AskInfoController {

    @Autowired
    private AskInfoService askInfoService;

    @Autowired
    private MatterService matterService;

    @RequestMapping("/matter/apply/{matterId}")
    public String apply(Model model, @PathVariable(name = "matterId") Long matterId) {
        MatterVo matterInfo = matterService.getMatter(matterId);
        model.addAttribute("matterInfo", matterInfo);
        return "ask/ask-edit";
    }

    @RequestMapping("/matter/create")
    public String create(AskInfoVo askInfoVo) {
        AskInfo askInfo = askInfoService.apply(askInfoVo);
        return "ask/ask-view";
    }
}
