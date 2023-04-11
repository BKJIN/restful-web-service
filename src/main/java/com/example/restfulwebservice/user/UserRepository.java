package com.example.restfulwebservice.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //DB관련 Bean
public interface UserRepository extends JpaRepository<Member, Integer> { //CRUD Method 사용 가능

}
