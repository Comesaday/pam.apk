package cn.comesaday.avt.example.retry;

import cn.comesaday.coe.core.basic.bean.result.JsonResult;

/**
 * <描述> BugTicketService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-27 10:57
 */
public interface BuyTicketService {

    JsonResult buy() throws Exception;

}
