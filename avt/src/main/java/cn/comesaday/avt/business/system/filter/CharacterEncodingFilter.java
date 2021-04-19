package cn.comesaday.avt.business.system.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <描述> 文件编码过滤器
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-10 19:43
 */
@WebFilter(urlPatterns = "/*", filterName = "CharacterEncodingFilter")
public class CharacterEncodingFilter implements Filter {

    // 日志框架
    private final static Logger logger = LoggerFactory.getLogger(CharacterEncodingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("CharacterEncodingFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        logger.info("CharacterEncodingFilter doFilter...");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        filterChain.doFilter(request , response);
    }

    @Override
    public void destroy() {
        logger.info("CharacterEncodingFilter destroy");
    }
}
