package cn.comesaday.avt.example.mode.controller;

import cn.comesaday.avt.example.mode.celue.bean.FactoryList;
import cn.comesaday.avt.example.mode.celue.sevice.ExampleService;
import cn.comesaday.coe.core.spring.loader.ApplicationContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <描述> ModeController
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-14 10:50
 */
@Controller
@RequestMapping("/mode")
public class ModeController extends ApplicationContextLoader {

    @Autowired
    private FactoryList<ExampleService, Integer> exampleServiceFactory;

    @RequestMapping("/test/{number}")
    @ResponseBody
    public String test(@PathVariable(name = "number") Integer number) {
        return exampleServiceFactory.getBean(number).apply();
    }

    @RequestMapping("/context")
    @ResponseBody
    public String context() {
        ExampleService exampleService = super.getBean("eatServiceImpl");
        return exampleService.apply();
    }
}
