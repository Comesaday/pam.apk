package cn.comesaday.avt.frame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <描述> FrameController
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-17 10:55
 */
@Controller
@RequestMapping("/frame")
public class FrameController {

    /**
     * <说明> 首页
     * @author ChenWei
     * @date 2021/3/17 10:56
     * @return java.lang.String
     */
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
