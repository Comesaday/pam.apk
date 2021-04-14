package cn.comesaday.avt.apply.delegate;

import cn.comesaday.avt.process.delegate.AbstractProcessDelegate;
import org.activiti.engine.delegate.DelegateExecution;

/**
 * <描述> AvtProcessDelegate流程方法
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-14 14:20
 */
public abstract class AbstractApplyDelegate extends AbstractProcessDelegate {

    /**
     * <说明> 检查事项配置
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 11:11
     * @return void
     */
    public abstract void checkMatterSetting(DelegateExecution delegateExecution);


    /**
     * <说明> 检查申请信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 11:10
     * @return void
     */
    public abstract void checkUserFill(DelegateExecution delegateExecution);


    /**
     * <说明> 初始化申请信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 11:11
     * @return void
     */
    public abstract void initAskInfo(DelegateExecution delegateExecution);


    /**
     * <说明> 初始化申请版本信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 15:04
     * @return void
     */
    public abstract void initAskTrack(DelegateExecution delegateExecution);

}
