package cn.comesaday.avt.apply.thread;

import cn.comesaday.avt.apply.model.AskInfo;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;

import java.util.concurrent.Callable;

/**
 * <描述> DoAskScanThread
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-01 19:10
 */
public class DoAskScanThread implements Callable<JsonResult> {

    private AskInfo askInfo;

    public DoAskScanThread(AskInfo askInfo) {
        this.askInfo = askInfo;
    }

    @Override
    public JsonResult call() throws Exception {
        return null;
    }
}
