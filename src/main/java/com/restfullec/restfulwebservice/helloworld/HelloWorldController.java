package com.restfullec.restfulwebservice.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloWorldController {

    // GET 방식이용
    // /hello-world
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    // RestController를 쓰면 자동으로 JSON 포맷으로 나옴.
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBeanPath(@PathVariable("name") String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }
}
