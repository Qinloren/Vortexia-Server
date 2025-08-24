package com.zeeyeh.vortexiaserver.filter;

import com.zeeyeh.vortexiaserver.provider.TokenProvider;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class LoginFilter implements Filter {

    @Resource
    TokenProvider tokenProvider;
    private static final List<String> ALLOWED_PATHS = Arrays.asList(
            ""
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        if (isAllowedPath(requestURI, request.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("无效的token");
            return;
        }
        String token = authorizationHeader.substring(6).trim();
        if (!tokenProvider.verifyToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("无效的token或已登录失效");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isAllowedPath(String requestURI, String method) {
        if ("GET".equalsIgnoreCase(method)) {
            return ALLOWED_PATHS.stream().anyMatch(requestURI::matches);
        }
        if ("POST".equalsIgnoreCase(method)) {
            return ALLOWED_PATHS.stream().anyMatch(requestURI::matches);
        }
        return false;
    }
}
