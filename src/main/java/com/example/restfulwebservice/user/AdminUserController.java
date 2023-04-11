package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//스프링에서 선언되어 관리되고 있는 인스턴스 -> 빈
@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service) { //의존성 주입(생성자 통해)
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {
        List<Member> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "password");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    // GET /admin//users/1 or /admin/users/10 -> String 형태로 전달 됨(http) -> /admin/v1/users/1
//    @GetMapping("/v1/users/{id}") //URI 이용한 버전 관리
//    @GetMapping(value = "/users/{id}/", params = "version=1") //Request Parameter 이용 버전 관리 (http://localhost:8088/admin/users/1/?version=1)
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1") //Header 이용 버전 관리
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json") //마임타입?? (Header key Accept로)
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
        Member user = service.findOne(id); //ctrl+alt+v

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "password", "ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }

//    @GetMapping("/v2/users/{id}") //URI 이용한 버전 관리
//    @GetMapping(value = "/users/{id}/", params = "version=2") //Request Parameter 이용 버전 관리
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2") //Header 이용 버전 관리
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        Member user = service.findOne(id); //ctrl+alt+v

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> User2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2); //id, name, joinDate, password, ssn
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);

        return mapping;
    }
}
