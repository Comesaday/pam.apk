package cn.comesaday.avt.example.retry.spring;

import cn.comesaday.avt.example.retry.BuyTicketService;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import cn.comesaday.coe.core.basic.exception.PamException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * <描述> SpringRetryBuyTicketServiceImpl
 * <详细背景> 基于spring-retry的重试机制
 * @author: ChenWei
 * @CreateAt: 2021-05-28 09:13
 */
@Service
public class SpringRetryBuyTicketServiceImpl implements BuyTicketService {

    @Retryable(value = PamException.class, maxAttempts = 5, backoff = @Backoff(delay = 10, multiplier = 1.5))
    @Override
    public JsonResult buy() throws Exception {
        if (1 == 1) {
            System.out.println("method buy execute exception");
            throw new PamException("222");
        }
        return new JsonResult(false);
    }

    @Recover
    public JsonResult recover() {
        return new JsonResult(true);
    }
}
