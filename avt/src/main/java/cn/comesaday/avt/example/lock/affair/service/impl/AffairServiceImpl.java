package cn.comesaday.avt.example.lock.affair.service.impl;

import cn.comesaday.avt.example.lock.affair.manager.AffairManager;
import cn.comesaday.avt.example.lock.affair.model.Affair;
import cn.comesaday.avt.example.lock.affair.service.AffairService;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <描述>
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-11 14:05
 */
@Service
@Transactional
public class AffairServiceImpl extends BaseService<Affair, Long> implements AffairService {

    private final static Logger logger = LoggerFactory.getLogger(AffairService.class);

    @Autowired
    private AffairManager affairManager;

    @Override
    public void test1() throws Exception {
        Affair affair = affairManager.findForTest(1L);
        logger.info("test1读取完成");
        affair.setAge(200);
        Thread.sleep(10 * 1000);
        Affair save = affairManager.save(affair);
        logger.info("test1插入完成");
    }

    @Override
    public void test2() {
        Affair affair =  affairManager.findForTest(1L);
        logger.info("test2读取完成");
        //   @Lock(LockModeType.PESSIMISTIC_WRITE) 加X锁，防止丢失更新
        affair.setAge(100);
        affair.setVersion(affair.getVersion() + 1);
        affairManager.save(affair);
        affairManager.flush();
        logger.info("test2插入完成");
    }

    @Override
    public void init() {
        Affair affair = new Affair();
        affair.setName("111");
        affair.setAge(12);
        affairManager.save(affair);
    }
}
