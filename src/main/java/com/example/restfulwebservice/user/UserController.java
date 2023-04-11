package com.example.restfulwebservice.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo; //static method 가져옴
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//스프링에서 선언되어 관리되고 있는 인스턴스 -> 빈
@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) { //의존성 주입(생성자 통해)
        this.service = service;
    }

    @GetMapping("/users")
    public List<Member> retrieveAllUsers() {
        return service.findAll();
    }

    // GET /users/1 or /users/10 -> String 형태로 전달 됨(http)
    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel<Member>> retrieveUser(@PathVariable int id) {
        Member user = service.findOne(id); //ctrl+alt+v

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        //HATEOAS
        EntityModel entityModel = EntityModel.of(user);

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("/users")
    public ResponseEntity<Member> createUser(@Valid @RequestBody Member user) {
        Member savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") //Headers에 Location
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build(); //201 Created 상태코드 반환 (Post, Put 성공시)
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        Member user = service.deleteById(id);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }
}
