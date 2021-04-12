package cn.comesaday.avt.user.service;

import cn.comesaday.avt.user.model.UserGroup;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <描述> UserGroupService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-12 14:03
 */
@Service
@Transactional
public class UserGroupService extends BaseService<UserGroup, Long> {
}
