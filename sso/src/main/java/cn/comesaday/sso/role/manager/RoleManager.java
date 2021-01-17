package cn.comesaday.sso.role.manager;

import cn.comesaday.coe.core.jpa.bean.repository.MyRepository;
import cn.comesaday.sso.role.model.Role;
import org.springframework.stereotype.Repository;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-29 20:27
 */
@Repository
public interface RoleManager extends MyRepository<Role, Long> {

}
