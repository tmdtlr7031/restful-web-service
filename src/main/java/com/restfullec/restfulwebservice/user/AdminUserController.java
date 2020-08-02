package com.restfullec.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    private UserDaoService service;

    // 생성자를 통한 의존성 주입
    public AdminUserController(UserDaoService userDaoService) {
        this.service = userDaoService;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers () {
        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "password"); // 클리이언트에게 노출할 필드값

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter); // @JsonFilter 이름값

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters); // 필터적용

        return mapping;
    }

//    @GetMapping("/v1/users/{id}")
//    @GetMapping(value = "/users/{id}/", params = "version=1")
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable("id") int userId) { // MappingJacksonValue : 필터가 적용된 데이터
        User user = service.findOne(userId);

        /*
        * 예외상황처리
        *   - 500에러인 경우 서버의 에러위치가 나오기때문에 처리해줘야함!
        */
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }

        // 왜이렇게..? : 나중에 버전컨트롤링? 할때 유용하다고함. 능동적으로 제어가능하기 때문.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn"); // 클리이언트에게 노출할 필드값

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter); // @JsonFilter 이름값

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters); // 필터적용

        return mapping;
    }

    // 버전관리 (uri를 통한)
//    @GetMapping("/v2/users/{id}")
//    @GetMapping(value = "/users/{id}/", params = "version=2") // requestParameter를 이용하는 방법. 뒤에 /로 끝나야함 (뒤에 params값이 전달되야하기 때문)
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2") // header 값을 이용. X-API-VERSION=2는 임의로 작성한 값임
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json") // MIME 타입이용 (company.appv1는 임의로 작성, +json는 전달하고자하는 타입 명시)
    public MappingJacksonValue retrieveUserV2(@PathVariable("id") int userId) { // MappingJacksonValue : 필터가 적용된 데이터
        User user = service.findOne(userId);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }

        // UserV2로 변환
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters); // 필터적용

        return mapping;
    }
}
