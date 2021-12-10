package org.zerock.boardWithReply.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.boardWithReply.entity.Board;
import org.zerock.boardWithReply.entity.Reply;

import java.util.List;
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


    //특정 게시물의 댓글들 가져오는 메서드 테스트
    @Test
    public void getRepliesByBoardOrderByBno(){

        List<Reply> result = repository.getReplyList(202L);

        result.forEach(s-> System.out.println(">>>>>"+s));
    }

    @Test
    public void test(){

        //게시글 301번에 댓글 5개 넣기
        IntStream.rangeClosed(2,6).forEach(i->{

            Board board = Board.builder().bno(301L).build();

            Reply reply = Reply.builder()
                    .text("ㄷㄷㄷㄷㄷ")
                    .replyer("user100")
                    .board(board)
                    .build();

            repository.save(reply);

        });
    }
}
