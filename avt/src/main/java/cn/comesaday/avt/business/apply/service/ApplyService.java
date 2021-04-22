package cn.comesaday.avt.business.apply.service;

import cn.comesaday.avt.business.apply.model.ApplyFormData;
import cn.comesaday.avt.business.apply.model.ApplyInfo;
import cn.comesaday.avt.business.apply.vo.AskInfoVo;
import cn.comesaday.avt.business.matter.model.Matter;
import cn.comesaday.avt.business.matter.service.MatterService;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.common.util.JsonUtil;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import cn.comesaday.coe.core.basic.exception.PamException;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <描述> AskInfoService
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-08 15:22
 */
@Transactional
@Service
public class ApplyService extends BaseService<ApplyInfo, Long> {

    @Autowired
    private MatterService matterService;

    @Autowired
    private ApplyFormDataService applyFormDataService;

    @Autowired
    private RuntimeService runtimeService;

    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(ApplyService.class);

    // 线程执行池
    private final static ExecutorService executorService = Executors.newFixedThreadPool(NumConstant.I10);

    /**
     * <说明> 事项申请
     *
     * @param askInfoVo 申请信息
     * @return cn.comesaday.avt.business.apply.model.AskInfo
     * @author ChenWei
     * @date 2021/3/29 19:52
     */
    public JsonResult apply(AskInfoVo askInfoVo) {
        JsonResult result = new JsonResult(Boolean.TRUE, askInfoVo);
        try {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Authentication.setAuthenticatedUserId(String.valueOf(askInfoVo.getApplyId()));
                    // 初始化流程变量数据
                    String sessionId = RandomStringUtils.randomNumeric(NumConstant.I10);
                    ProcessVariable variable = new ProcessVariable(sessionId, askInfoVo);
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("processInfo", variable);
                    // 开启流程
                    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(askInfoVo.getMatterCode(), variables);
                    String instanceId = processInstance.getProcessInstanceId();
                    logger.info("[提交申请]成功,流程实例ID:{},申请信息:{}", instanceId, JsonUtil.toJson(askInfoVo));
                }
            });
        } catch (Exception e) {
            result.setError("[提交申请]异常:" + e + ",申请信息:" + JsonUtil.toJson(askInfoVo));
            logger.error("]提交申请]异常:{},申请信息:{}", e, JsonUtil.toJson(askInfoVo));
        }
        return result;
    }


    /**
     * <说明> 检查申请信息
     *
     * @param askInfoVo AskInfoVo
     * @return void
     * @author ChenWei
     * @date 2021/4/7 15:38
     */
    public void checkAskInfo(AskInfoVo askInfoVo) throws PamException {
        // 申请明细信息
        askInfoVo.setApplyId(1L);
        askInfoVo.setApplyName("11");
        if (null == askInfoVo) {
            throw new PamException("未查询到申请信息");
        }
        if (null == askInfoVo.getMatter()) {
            throw new PamException("申请信息未关联事项");
        }
        if (null == askInfoVo.getApplyId()
                || StringUtils.isEmpty(askInfoVo.getApplyName())) {
            throw new PamException("申请人信息为空");
        }
        if (CollectionUtils.isEmpty(askInfoVo.getAskInfos())) {
            throw new PamException("申请表单信息为空");
        }
    }


    /**
     * <说明> 初始化事项主表信息
     *
     * @param askInfoVo 申请信息
     * @return cn.comesaday.avt.business.apply.model.AskInfo
     * @author ChenWei
     * @date 2021/3/29 19:52
     */
    public ApplyInfo initAskMainInfo(AskInfoVo askInfoVo, Matter matter) {
        ApplyInfo applyInfo = new ApplyInfo();
        applyInfo.setMatterCode(matter.getCode());
        applyInfo.setMatterId(matter.getId());
        applyInfo.setMatterName(matter.getName());
        applyInfo.setApplyId(null);
        applyInfo.setApplyName(null);
        applyInfo.setStatus(NumConstant.I1);
        return this.save(applyInfo);
    }

    /**
     * <说明> 查询申请信息
     *
     * @param askId Long
     * @return cn.comesaday.avt.business.apply.vo.AskInfoVo
     * @author ChenWei
     * @date 2021/4/1 17:35
     */
    public AskInfoVo queryDetail(Long askId) throws PamException {
        AskInfoVo askInfoVo = new AskInfoVo();
        ApplyInfo applyInfo = this.findOne(askId);
        if (null == applyInfo) {
            throw new PamException("申请信息不存在");
        }
        askInfoVo.setAskId(askId);
        askInfoVo.setApplyInfo(applyInfo);
        askInfoVo.setMatter(matterService.findOne(applyInfo.getMatterId()));
        askInfoVo.setApplyId(applyInfo.getApplyId());
        askInfoVo.setApplyName(applyInfo.getApplyName());
        askInfoVo.setAskTime(applyInfo.getCreateAt());
        askInfoVo.setAskInfos(getAskDatas(askId));
        return askInfoVo;
    }

    /**
     * <说明> 获取申请表单信息
     *
     * @param askId Long
     * @return java.util.List<cn.comesaday.avt.business.apply.model.AskFormData>
     * @author ChenWei
     * @date 2021/4/15 17:20
     */
    public List<ApplyFormData> getAskDatas(Long askId) {
        return applyFormDataService.findAllByProperty("askId", askId);
    }
}