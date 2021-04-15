package cn.comesaday.avt.apply.service;

import cn.comesaday.avt.apply.model.AskFormData;
import cn.comesaday.avt.apply.model.AskInfo;
import cn.comesaday.avt.apply.vo.AskInfoVo;
import cn.comesaday.avt.matter.model.Matter;
import cn.comesaday.avt.matter.service.MatterService;
import cn.comesaday.avt.process.variable.ProcessVariable;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.common.util.JsonUtil;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import cn.comesaday.coe.core.basic.exception.PamException;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
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
 * @author: ChenWei
 * @CreateAt: 2021-03-08 15:22
 */
@Transactional
@Service
public class AskInfoService extends BaseService<AskInfo, Long> {

    @Autowired
    private MatterService matterService;

    @Autowired
    private AskFormDataService askFormDataService;

    @Autowired
    private RuntimeService runtimeService;

    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(AskInfoService.class);

    // 线程执行池
    private final static ExecutorService executorService = Executors.newFixedThreadPool(NumConstant.I10);

    /**
     * <说明> 事项申请
     * @param askInfoVo 申请信息
     * @author ChenWei
     * @date 2021/3/29 19:52
     * @return cn.comesaday.avt.apply.model.AskInfo
     */
    public JsonResult apply(AskInfoVo askInfoVo) {
        JsonResult result = new JsonResult(Boolean.TRUE, askInfoVo);
        try {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Authentication.setAuthenticatedUserId(String.valueOf(askInfoVo.getApplyId()));
                    ProcessVariable variable = new ProcessVariable();
                    askInfoVo.setAskTime(new Date());
                    variable.setAskInfoVo(askInfoVo);
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("processInfo", variable);
                    // 开启流程
                    logger.info("[提交申请]进行,sessionId:{},流程变量:{}", variable.getSessionId(), JsonUtil.toJson(variable));
                    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(askInfoVo.getMatterCode(), variables);
                    String processInstanceId = processInstance.getProcessInstanceId();
                    logger.info("[提交申请]成功,流程实例ID:{},申请信息:{}", processInstanceId, JsonUtil.toJson(askInfoVo));
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
     * @param askInfoVo AskInfoVo
     * @author ChenWei
     * @date 2021/4/7 15:38
     * @return void
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
     * @param askInfoVo 申请信息
     * @author ChenWei
     * @date 2021/3/29 19:52
     * @return cn.comesaday.avt.apply.model.AskInfo
     */
    public AskInfo initAskMainInfo(AskInfoVo askInfoVo, Matter matter) {
        AskInfo askInfo = new AskInfo();
        askInfo.setMatterCode(matter.getCode());
        askInfo.setMatterId(matter.getId());
        askInfo.setMatterName(matter.getName());
        askInfo.setApplyId(null);
        askInfo.setApplyName(null);
        askInfo.setStatus(NumConstant.I1);
        return this.save(askInfo);
    }

    /**
     * <说明> 查询申请信息
     * @param askId Long
     * @author ChenWei
     * @date 2021/4/1 17:35
     * @return cn.comesaday.avt.apply.vo.AskInfoVo
     */
    public AskInfoVo queryDetail(Long askId) throws PamException {
        AskInfoVo askInfoVo = new AskInfoVo();
        AskInfo askInfo = this.findOne(askId);
        if (null == askInfo) {
            throw new PamException("申请信息不存在");
        }
        askInfoVo.setAskId(askId);
        askInfoVo.setAskInfo(askInfo);
        askInfoVo.setMatter(matterService.findOne(askInfo.getMatterId()));
        askInfoVo.setApplyId(askInfo.getApplyId());
        askInfoVo.setApplyName(askInfo.getApplyName());
        askInfoVo.setAskTime(askInfo.getCreateAt());
        askInfoVo.setAskInfos(getAskDatas(askId));
        return askInfoVo;
    }

    /**
     * <说明> 获取申请表单信息
     * @param askId Long
     * @author ChenWei
     * @date 2021/4/15 17:20
     * @return java.util.List<cn.comesaday.avt.apply.model.AskFormData>
     */
    public List<AskFormData> getAskDatas(Long askId) {
        return askFormDataService.findAllByProperty("askId", askId);
    }
}
