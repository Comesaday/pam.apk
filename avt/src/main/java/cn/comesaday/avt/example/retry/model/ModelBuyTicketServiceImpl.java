package cn.comesaday.avt.example.retry.model;

import cn.comesaday.avt.example.retry.BuyTicketService;
import cn.comesaday.avt.example.retry.RetryTemplate;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <描述> BuyTicketServiceImpl
 * <详细背景> 模板方法重试机制
 * @author: ChenWei
 * @CreateAt: 2021-05-27 10:58
 */
@Service
public class ModelBuyTicketServiceImpl implements BuyTicketService {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public JsonResult buy() throws Exception {
        return new RetryTemplate() {
            @Override
            public JsonResult doService() throws Exception {
                JsonResult result = new JsonResult(false);
                if (!result.isSuccess()) {
                    throw new Exception("service execute error");
                }
                return result;
            }
        }.setTime(3)
                .setSleep(500)
                .execute();
    }
}
