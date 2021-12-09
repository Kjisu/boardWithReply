package org.zerock.boardWithReply.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.boardWithReply.entity.Member;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository repository;

    //회원 데이터 100개 insert
    @Test
    public void insertMembers(){

        for(int i=1;i<=100;i++){
            //given
            Member member = Member.builder()
                    .email("user" + i + "@zerock.com")
                    .password("1234")
                    .name("user" + i)
                    .build();
            //when
            repository.save(member);
        }
    }
}
