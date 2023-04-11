package com.example.restfulwebservice.helloWorld;

//lombok
//setting -> Annotation Processors -> Enable annotation processing 체크
//plugin 설치
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldBean {
    private String message;
//    public HelloWorldBean(String message) { //생성자
//        this.message = message;
//    }
}
