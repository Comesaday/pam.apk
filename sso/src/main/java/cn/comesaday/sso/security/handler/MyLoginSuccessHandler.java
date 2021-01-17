package cn.comesaday.sso.security.handler;

import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import org.apache.http.entity.ContentType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-30 19:31
 */
public class MyLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        JsonResult result = new JsonResult();
        result.setSuccess("success");
        PrintWriter out = response.getWriter();
        out.append(result.toString());
        out.flush();
        out.close();
    }
}
