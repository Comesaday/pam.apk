package cn.comesaday.sso.security.handler;

import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import org.apache.http.entity.ContentType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

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
 * @CreateAt: 2021-01-08 17:50
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        PrintWriter out = response.getWriter();
        JsonResult result = new JsonResult();
        result.setError(String.valueOf(HttpServletResponse.SC_FORBIDDEN), "无权限访问");
        out.write(result.toString());
        out.flush();
        out.close();
    }
}
