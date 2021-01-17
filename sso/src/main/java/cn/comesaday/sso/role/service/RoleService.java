package cn.comesaday.sso.role.service;

import cn.comesaday.coe.core.basic.service.BaseService;
import cn.comesaday.coe.enhance.jdbc.JdbcEnhance;
import cn.comesaday.sso.role.manager.RoleManager;
import cn.comesaday.sso.role.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-29 20:29
 */
@Service
@Transactional
public class RoleService extends BaseService<Role, Long> {

    @Autowired
    private JdbcEnhance jdbcEnhance;

    @Autowired
    private RoleManager roleManager;

    /**
     * 根据用户id查角色
     * @param userId
     * @return
     */
    public List<Role> getRolesByUid(Long userId) {
        String sql = "select r.* from sys_role r left join sys_user_role u on r.code = u.role_code where u.user_id = " + userId;
        return jdbcEnhance.queryForList(sql, Role.class);
    }

    public List<Role> findAll() {
        return roleManager.findAll();
    }
}
