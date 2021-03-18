package cn.comesaday.avt.apply.vo;

import cn.comesaday.avt.apply.model.AskFormData;

import java.util.Date;
import java.util.List;

/**
 * <描述> 申请信息vo
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-17 14:37
 */
public class AskInfoVo {

    // 申请人ID
    private Long applyId;

    // 申请人姓名
    private String applyName;

    // 流程code
    private String modelCode;

    private Date askTime;

    // 申请信息
    private List<AskFormData> askInfos;


}
