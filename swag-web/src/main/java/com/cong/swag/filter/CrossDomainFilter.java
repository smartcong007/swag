package com.cong.swag.filter;

import com.cong.swag.common.util.ConfigUtils;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-01-04
 */
public class CrossDomainFilter implements Filter {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(CrossDomainFilter.class);

    private static String urlAllowed = ConfigUtils.getValue("domain.allowed");

    @Value("${domain.default}")
    private static String defaultDomain = ConfigUtils.getValue("domain.default");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String clientOrigin = req.getHeader("Origin");
        LOGGER.info("当前请求源为：{}", clientOrigin);
        if (StringUtils.isNotEmpty(clientOrigin) && urlAllowed.indexOf(clientOrigin) != -1) {
            resp.setHeader("Access-Control-Allow-Origin", clientOrigin);
        }else {
            resp.setHeader("Access-Control-Allow-Origin", defaultDomain);
        }
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Max-Age", "0");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        resp.setHeader("Access-Control-Allow-Credentials", Boolean.TRUE.toString());
        filterChain.doFilter(servletRequest, resp);
    }

    @Override
    public void destroy() {

    }
}
