package org.zerock.boardWithReply.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.boardWithReply.entity.Board;
import org.zerock.boardWithReply.entity.Reply;

import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository repository;

    //댓글 300개 넣기
    @Test
    public void insertReplies(){

        //for문 대신 IntStream 생성 후, forEach() 사용하여 작업
        IntStream.rangeClosed(101,200)
                .forEach(i -> {

                    long bno = i+101;

                    //board 객체 생성
                    Board board = Board.builder().bno(bno).build();

                    //Reply 객체 생성
                    Reply reply = Reply.builder()
                            .rno((long)i)
                            .text("text..." + i+201)
                            .replyer("user" + i+201)
                            .board(board).build();

                    //쿼리실행
                    repository.save(reply);
                });
    }
}
