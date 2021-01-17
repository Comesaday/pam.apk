package cn.comesaday.avt.demo.service;

import cn.comesaday.avt.demo.manager.DemoManager;
import cn.comesaday.avt.demo.model.Demo;
import cn.comesaday.coe.core.basic.service.BaseService;
import cn.comesaday.coe.enhance.jdbc.JdbcEnhance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-09-06 19:26
 */
@Service
@Transactional
public class DemoService extends BaseService<Demo, Long> {

    @Autowired
    private DemoManager demoManager;

    @Autowired
    private JdbcEnhance jdbcEnhance;

    public Map<String, Object> install() {
        Demo demo = new Demo();
        demo.setName("2222");
        demoManager.save(demo);
        Map<String, Object> data = new HashMap<>();
        data.put("demo", demo);
        String sql = "select id,is_disabled from platform_demo where id = " + (demo.getId() - 1);
        List<Demo> list = jdbcEnhance.queryForList(sql, Demo.class);
        return data;
    }
}
