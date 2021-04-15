package cn.comesaday.avt.process.service;

import cn.comesaday.avt.process.model.ProcessInfo;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <描述> 申请工作流开始处理
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-01 17:23
 */
@Service
@Transactional
public class ProcessInfoService extends BaseService<ProcessInfo, Long> {

}