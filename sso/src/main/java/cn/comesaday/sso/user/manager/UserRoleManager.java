package cn.comesaday.sso.user.manager;

import cn.comesaday.coe.core.jpa.bean.repository.MyRepository;
import cn.comesaday.sso.user.model.UserRole;
import org.springframework.stereotype.Repository;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-29 20:28
 */
@Repository
public interface UserRoleManager extends MyRepository<UserRole, Long> {

}
