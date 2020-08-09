package com.restfullec.restfulwebservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // 스프링부트가 실행되면서 메모리에 설정정보를 같이 로딩함
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("skatmdtlr")
                .password("{noop}1234") // {noop} : 인코딩 없이 바로 사용한다는 뜻
                .roles("USER"); // 권한

    }
}
