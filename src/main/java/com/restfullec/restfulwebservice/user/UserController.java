package com.restfullec.restfulwebservice.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        User user = service.findOne(userId);

        /*
        * 예외상황처리
        *   - 500에러인 경우 서버의 에러위치가 나오기때문에 처리해줘야함!
        */
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
        return user;
    }

    // POST로 해당 url 요청 시
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) { // POST, PUT과같은 메서드에선
                                                    // 클라이언트에서 폼데이터가 아닌 JSON이나 XML같이 Object 형태의 데이터를 받기 위해서 @RequestBody 선언
        User savedUser = service.save(user);

        /*
        * 추가) REST API 관점의 HTTP 상태코드 설명 참고 : https://sanghaklee.tistory.com/61
        * https://blog.outsider.ne.kr/903 참고
        * ServletUriComponentsBuilder : 서블릿 요청에서 사용가능한 URL 정보를 복사하는 정적 메서드 제공
        * fromCurrentRequest : 현재 요청 되어 진 request 값을 사용. (ex. http:localhost:8080/user/1)
        * path : 추가할 경로
        * buildAndExpand : {id} 값에 맵핑됨. 즉 path 의 {}에 맵핑.
        *
        * 왜 굳이?
        *   - 클라이언트 측에서는 새로 생성된 user의 id값을 알지 못함. 그렇기 때문에 상세 조회가 불가능하고 이를 위해 서버에 물어봐야함.
        *     하지만 이렇게하면 생성 후 해더의 location값에 id값을 넣어줄 수 있고 트래픽 감소 효과를 줌. 그리고 응답코드를 구분하여 관리 가능
        *
        * ResponseEntity
        *   - HttpHeader와 body를 가질 수 있음 (ex. ResponseEntity.status(HttpStatus.OK).body(testVO) --> 상태코드제어 + body에 데이터 전달)
        *   - body에 담긴 데이터는 클라이언트 단에서 json || xml 형식으로 return됨
        * */
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
