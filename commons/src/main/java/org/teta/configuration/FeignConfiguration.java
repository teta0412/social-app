package org.teta.configuration;

import feign.RequestInterceptor;
import feign.form.spring.SpringFormEncoder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import static org.teta.constants.HeaderConstants.AUTH_USER_ID_HEADER;


@Configuration
public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
                template.header(AUTH_USER_ID_HEADER, request.getHeader(AUTH_USER_ID_HEADER));
            }
        };
    }

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    public FeignConfiguration(ObjectFactory<HttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
    }

    @Bean
    public Encoder feignEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
}
