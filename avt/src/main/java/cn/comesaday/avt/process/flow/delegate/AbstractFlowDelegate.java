package cn.comesaday.avt.process.flow.delegate;

import cn.comesaday.avt.process.flow.handler.impl.DefaultFlowHandler;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <描述> ProcessDelegate
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-14 19:50
 */
public abstract class AbstractFlowDelegate extends DefaultFlowHandler implements JavaDelegate {

    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(AbstractFlowDelegate.class);

}
