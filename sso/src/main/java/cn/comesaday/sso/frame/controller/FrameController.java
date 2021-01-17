package cn.comesaday.sso.frame.controller;

import cn.comesaday.sso.api.anno.ApiPush;
import cn.comesaday.sso.api.service.ApiService;
import cn.comesaday.sso.role.model.Role;
import cn.comesaday.sso.role.service.RoleService;
import cn.comesaday.sso.user.manager.UserRoleManager;
import cn.comesaday.sso.user.model.UserInfo;
import cn.comesaday.sso.user.model.UserRole;
import cn.comesaday.sso.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-30 11:36
 */
@Controller
@RequestMapping("/frame/")
public class FrameController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleManager userRoleManager;

    @Autowired
    private ApiService apiService;

    @GetMapping("login/page")
    @ApiPush(name = "登录页面", apiCode = "apk.sso.frame.login.page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("register/page")
    @ApiPush(name = "注册页面", apiCode = "apk.sso.frame.register.page")
    public String registerPage() {
        return "register";
    }

    @PostMapping("register")
    @ApiPush(name = "注册新用户", apiCode = "apk.sso.frame.register")
    public String register(UserInfo userInfo) {
        userInfo = new UserInfo();
        userInfo.setUsername("admin");
        userInfo.setPassword(passwordEncoder.encode("admin"));

        Role role = new Role();
        role.setCode("admin");
        role.setName("管理员");
        roleService.save(role);
        userInfoService.save(userInfo);

        UserRole userRole = new UserRole();
        userRole.setRoleCode(role.getCode());
        userRole.setUserId(userInfo.getId());
        userRoleManager.save(userRole);
        return "login";
    }

    @RequestMapping("/api/init")
    @ApiPush(name = "api注册", apiCode = "apk.sso.frame.api.init")
    public void api() {
        apiService.apis();
    }

    @RequestMapping("/index")
    @ApiPush(name = "主页面", apiCode = "apk.sso.index")
    public String index(Model model) {
        return "index";
    }
}
