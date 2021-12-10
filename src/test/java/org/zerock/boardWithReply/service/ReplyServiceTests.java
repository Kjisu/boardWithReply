package org.zerock.boardWithReply.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.boardWithReply.dto.ReplyDTO;

import java.util.List;

@SpringBootTest
public class ReplyServiceTests {

    @Autowired
    private ReplyService service;

    //댓글 목록 출력
    @Test
    public void getReplyList(){

        List<ReplyDTO> result = service.getReplyList(301L);

        result.forEach(dto -> System.out.println(dto));
    }


}
