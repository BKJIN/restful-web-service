package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    // Member : Post -> 1 : (0~N), Main : Sub -> Parent -> Child
    @ManyToOne(fetch = FetchType.LAZY) //지연 로딩 -> Post 데이터가 로딩되는 시점에 필요한 사용자 데이터 가져 옴
    @JsonIgnore
    private Member member;
}
