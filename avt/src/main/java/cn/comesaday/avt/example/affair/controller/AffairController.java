package cn.comesaday.avt.example.affair.controller;

import cn.comesaday.avt.example.affair.service.AffairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <描述>
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-11 14:09
 */
@RestController
@RequestMapping("/example/arrair")
public class AffairController {

    @Autowired
    private AffairService affairService;

    @RequestMapping("/init")
    public void init() {
        affairService.init();
    }

    @RequestMapping("/test1")
    public void test1() {
        affairService.test1();
    }

    @RequestMapping("/test2")
    public void test2() {
        affairService.test2();
    }
}
