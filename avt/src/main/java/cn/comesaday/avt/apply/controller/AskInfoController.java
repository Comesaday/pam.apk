package cn.comesaday.avt.apply.controller;

import cn.comesaday.coe.core.basic.bean.result.JsonResult;
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

    public JsonResult test() {
        return new JsonResult();
    }
}
