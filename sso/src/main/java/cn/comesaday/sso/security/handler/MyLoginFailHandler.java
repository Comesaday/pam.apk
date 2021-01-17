package cn.comesaday.sso.security.handler;

import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import org.apache.http.entity.ContentType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <描述> 登录失败处理器
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-30 19:31
 */
public class MyLoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        JsonResult result = new JsonResult();
        result.setError(exception.getMessage());
        PrintWriter out = response.getWriter();
        out.append(result.toString());
        out.flush();
        out.close();
    }
}
