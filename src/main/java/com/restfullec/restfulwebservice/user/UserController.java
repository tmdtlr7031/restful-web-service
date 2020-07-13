package com.restfullec.restfulwebservice.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserDaoService service;

    // 생성자를 통한 의존성 주입
    public UserController(UserDaoService userDaoService) {
        this.service = userDaoService;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers () {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable("id") int userId) {
        return service.findOne(userId);
    }

    // POST로 해당 url 요청 시
    @PostMapping("/users")
    public void createUser(@RequestBody User user) { // POST, PUT과같은 메서드에선
                                                    // 클라이언트에서 폼데이터가 아닌 JSON이나 XML같이 Object 형태의 데이터를 받기 위해서 @RequestBody 선언
        User savedUser = service.save(user);
    }
}
