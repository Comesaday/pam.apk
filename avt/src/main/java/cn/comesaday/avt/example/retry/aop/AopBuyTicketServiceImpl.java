package cn.comesaday.avt.example.retry.aop;

import cn.comesaday.avt.example.retry.BuyTicketService;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import org.springframework.stereotype.Service;

/**
 * <描述> AopBuyTicketServiceImpl
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-27 14:57
 */
@Service
public class AopBuyTicketServiceImpl implements BuyTicketService {

    @Override
    @Retry(time = 5, sleep = 10)
    public JsonResult buy() throws Exception {
        JsonResult result = new JsonResult(false);
        if (!result.isSuccess()) {
            throw new Exception("error");
        }
        return result;
    }
}
