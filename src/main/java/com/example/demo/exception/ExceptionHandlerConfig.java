package com.example.demo.exception;

import java.util.*;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.WebRequest;

@Configuration
public class ExceptionHandlerConfig {

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
                List<FieldError> errors = (List<FieldError>) errorAttributes.get("errors");
                if (errors != null) {
                    Map<String, String> mapErrors = new HashMap<>();
                    errors.forEach(error -> {
                        mapErrors.put(error.getField(), error.getDefaultMessage());
                    });
                    errorAttributes.put("errors", mapErrors);
                }
                return errorAttributes;
            }
        };
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
}