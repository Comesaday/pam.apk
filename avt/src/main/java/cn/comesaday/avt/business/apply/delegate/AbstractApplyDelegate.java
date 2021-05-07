package cn.comesaday.avt.business.apply.delegate;

import cn.comesaday.avt.process.flow.delegate.AbstractFlowDelegate;
import org.activiti.engine.delegate.DelegateExecution;

/**
 * <描述> AvtProcessDelegate流程方法
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-14 14:20
 */
public abstract class AbstractApplyDelegate extends AbstractFlowDelegate {


    /**
     * <说明> 检查事项配置
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 11:11
     * @return void
     */
    public abstract void check(DelegateExecution delegateExecution);


    /**
     * <说明> 初始化申请信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 11:11
     * @return void
     */
    public abstract void init(DelegateExecution delegateExecution);


    /**
     * <说明> 获取审批结果
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 15:04
     * @return void
     */
    public abstract Boolean agree(DelegateExecution delegateExecution);


    /**
     * <说明> 获取检查结果
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 15:04
     * @return void
     */
    public abstract Boolean checked(DelegateExecution delegateExecution);

}
