package cn.comesaday.sso.user.service;

import cn.comesaday.coe.core.basic.service.BaseService;
import cn.comesaday.sso.role.model.Role;
import cn.comesaday.sso.role.service.RoleService;
import cn.comesaday.sso.user.manager.UserManager;
import cn.comesaday.sso.user.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-29 19:11
 */
@Service
@Transactional
public class UserInfoService extends BaseService<UserInfo, Long> implements UserDetailsService {

    @Autowired
    private UserManager sysUserManager;

    @Autowired
    private RoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInfo user = sysUserManager.loadUserByUsername(userName);
        if (null == user) {
            throw new UsernameNotFoundException("user not found");
        }
        List<Role> roles = sysRoleService.getRolesByUid(user.getId());
        List<SimpleGrantedAuthority> authorities = Collections.EMPTY_LIST;
        if (null != roles && roles.size() > 0) {
            authorities = new ArrayList<>();
            for (Role role : roles) {
                String roleCode = "ROLE_" + role.getCode();
                authorities.add(new SimpleGrantedAuthority(roleCode));
            }
        }
        return new User(userName, user.getPassword(), authorities);
    }
}
