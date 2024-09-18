package com.knu.salmon.api.global.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class MDCLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String requestId = UUID.randomUUID().toString();
        String requestURI = request.getRequestURI();

        MDC.put("request_id", requestId);
        MDC.put("request_uri", requestURI);

        try {
            log.info("REQUEST [ {} ][ {} ]", requestId, requestURI);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            log.error("ERROR [ {} ][ {} ] - Exception: {}", requestId, requestURI, e.getMessage());
        } finally {
            log.info("RESPONSE [ {} ][ {} ]", MDC.get("request_id"), requestURI);
            MDC.clear();
        }
    }
}
