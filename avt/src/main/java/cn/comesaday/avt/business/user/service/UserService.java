package cn.comesaday.avt.business.user.service;

import cn.comesaday.avt.business.user.model.User;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <描述> UserService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-12 14:02
 */
@Service
@Transactional
public class UserService extends BaseService<User, Long> {
}