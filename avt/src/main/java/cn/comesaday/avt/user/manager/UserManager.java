package cn.comesaday.avt.user.manager;

import cn.comesaday.avt.user.model.User;
import cn.comesaday.coe.core.jpa.bean.repository.MyRepository;

/**
 * <描述> UserManager
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-12 14:01
 */
public interface UserManager extends MyRepository<User, Long> {
}
