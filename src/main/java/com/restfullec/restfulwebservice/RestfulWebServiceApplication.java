package com.restfullec.restfulwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class RestfulWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulWebServiceApplication.class, args);
    }

    // 프로젝트 전반에 다국어 처리를 위해 서버가 초기화될 때 빈으로 등록하기 위한 것
    @Bean
    public SessionLocaleResolver localResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver(); // 세션을 통해 locale값 얻어올 것이기 때문
        localeResolver.setDefaultLocale(Locale.KOREA);
        return localeResolver;
    }
}
