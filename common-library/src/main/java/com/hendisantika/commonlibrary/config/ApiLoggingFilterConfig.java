package com.hendisantika.commonlibrary.config;

import com.hendisantika.commonlibrary.filter.ApiLoggingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
@Component
@ConditionalOnExpression("${api.logging.enabled:true}")
public class ApiLoggingFilterConfig {

    @Value("${api.logging.url-patterns}")
    private String[] urlPatterns;

    @Value("${api.logging.requestIdHeaderName:X-REQUEST-CORRELATION-ID}")
    private String requestIdHeaderName;

    @Value("${api.logging.requestIdMDCParamName:REQUEST_CORRELATION_ID}")
    private String requestIdMDCParamName;

    @Bean
    public FilterRegistrationBean<ApiLoggingFilter> loggingFilter() {
        FilterRegistrationBean<ApiLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiLoggingFilter(requestIdHeaderName, requestIdMDCParamName));
        registrationBean.addUrlPatterns(urlPatterns);
        return registrationBean;
    }
}
