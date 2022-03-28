package com.hendisantika.commonlibrary.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Filter;

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
}
