package com.example.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static List<Member> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new Member(1, "Kenneth",new Date(),"pass1","701010-1111111"));
        users.add(new Member(2, "Alice",new Date(),"pass2","801010-1111111"));
        users.add(new Member(3, "Elena",new Date(),"pass3","901010-1111111"));
    }

    public List<Member> findAll() {
        return users;
    }

    public Member save(Member user) {
        if(user.getId() == null) {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public Member findOne(int id) {
        for(Member user : users) {
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public Member deleteById(int id) {
        Iterator<Member> iterator = users.iterator();

        while(iterator.hasNext()) {
            Member user = iterator.next();

            if(user.getId() == id) {
                iterator.remove();
                return user;
            }
        }

        return null;
    }
}
