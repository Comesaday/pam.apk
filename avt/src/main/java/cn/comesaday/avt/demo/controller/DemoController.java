package cn.comesaday.avt.demo.controller;

import cn.comesaday.avt.demo.service.DemoService;
import cn.comesaday.coe.common.util.StreamUtil;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <Descripe>
 *
 * @author: ChenWei
 * @CreateAt: 2020-08-10 15:21
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    /**
     * 测试新增数据
     * @return
     */
    @RequestMapping("/insert")
    public JsonResult insert() {
        JsonResult result = new JsonResult();
        try {
            Map<String, Object> data = demoService.install();
            result.setSuccess(null, data);
        } catch (Exception e) {
            result.setError(e.getMessage());
        }
        return result;
    }


    @RequestMapping("/qr")
    public void qr(HttpServletResponse response) {
        StreamUtil.createQRCode("http://101.132.103.66:8848/nacos", 200, 200, "png", response);
    }

}
