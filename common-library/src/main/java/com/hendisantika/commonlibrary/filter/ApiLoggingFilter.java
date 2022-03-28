package com.hendisantika.commonlibrary.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-microservices-sample2
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 28/03/22
 * Time: 14.06
 * To change this template use File | Settings | File Templates.
 */
public class ApiLoggingFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiLoggingFilter.class);
    private final String requestIdHeaderName;
    private final String requestIdMDCParamName;

    public ApiLoggingFilter(String requestIdHeaderName, String requestIdMDCParamName) {
        this.requestIdHeaderName = requestIdHeaderName;
        this.requestIdMDCParamName = requestIdMDCParamName;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            Map<String, String> requestMap = this.getTypesafeRequestMap(httpServletRequest);
            String requestIdHeaderValue = httpServletRequest.getHeader(requestIdHeaderName);
            BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(httpServletRequest);
            BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(httpServletResponse);
            String requestId = requestIdHeaderValue != null ? requestIdHeaderValue
                    : UUID.randomUUID().toString();
            MDC.put(requestIdMDCParamName, requestId);
            final StringBuilder logRequest = new StringBuilder("HTTP ").append(httpServletRequest.getMethod())
                    .append(" \"").append(httpServletRequest.getServletPath()).append("\" ").append(", parameters=")
                    .append(requestMap).append(", body=").append(bufferedRequest.getRequestBody())
                    .append(", remote_address=").append(httpServletRequest.getRemoteAddr());
            LOGGER.info(logRequest.toString());
            try {
                chain.doFilter(bufferedRequest, bufferedResponse);
            } finally {
                final StringBuilder logResponse = new StringBuilder("HTTP RESPONSE ")
                        .append(bufferedResponse.getContent());
                LOGGER.info(logResponse.toString());
                MDC.clear();
            }
        } catch (Throwable a) {
            LOGGER.error(a.getMessage());
        }
    }

    private Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {
        Map<String, String> typesafeRequestMap = new HashMap<String, String>();
        Enumeration<?> requestParamNames = request.getParameterNames();
        while (requestParamNames.hasMoreElements()) {
            String requestParamName = (String) requestParamNames.nextElement();
            String requestParamValue;
            if (requestParamName.equalsIgnoreCase("password")) {
                requestParamValue = "********";
            } else {
                requestParamValue = request.getParameter(requestParamName);
            }
            typesafeRequestMap.put(requestParamName, requestParamValue);
        }
        return typesafeRequestMap;
    }
}
