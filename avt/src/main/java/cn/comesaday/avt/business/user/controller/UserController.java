package cn.comesaday.avt.business.user.controller;

import cn.comesaday.avt.business.apply.vo.ApprovalRequest;
import cn.comesaday.avt.business.user.service.UserService;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import cn.comesaday.coe.core.basic.bean.result.Result;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <描述> UserController
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-12 14:04
 */
@Controller
@RequestMapping("/user")
public class UserController {

    // 引入日志
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    /**
     * <说明> 获取人员任务
     * @param userId 人员ID
     * @author ChenWei
     * @date 2021/4/25 17:46
     * @return cn.comesaday.coe.core.basic.bean.result.JsonResult
     */
    @RequestMapping("/query/task/{userId}")
    @ResponseBody
    public JsonResult myTask(@PathVariable(name = "userId") String userId) {
        try {
            List<Task> taskList = userService.getUserTask(userId);
            List<String> taskIds = null;
            if (!CollectionUtils.isEmpty(taskList)) {
                taskIds = taskList.stream().map(task -> {
                    return task.getId();
                }).collect(Collectors.toList());
            }
            return Result.success("获取成功", taskIds);
        } catch (Exception e) {
            return Result.fail("获取个人任务异常:" + e.getMessage());
        }
    }

    /**
     * <说明> 审批任务
     * @param approvalRequest ApprovalRequestVo
     * @author ChenWei
     * @date 2021/4/23 10:59
     * @return cn.comesaday.coe.core.basic.bean.result.JsonResult
     */
    @RequestMapping(value = "/task/approval", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonResult approval(@RequestBody ApprovalRequest approvalRequest) {
        try {
            userService.approval(approvalRequest);
            return Result.success("审批成功");
        } catch (Exception e) {
            logger.error("审批异常:" + e.getMessage(), e);
            return Result.fail("审批异常:" + e.getMessage());
        }
    }
}
