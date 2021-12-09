package org.zerock.boardWithReply.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.boardWithReply.entity.Board;
import org.zerock.boardWithReply.entity.Member;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository repository;

    //게시글 데이터 100개 넣기
    @Test
    public void insertDummies(){

        //for반복문 대신 Stream사용해보자
        IntStream.rangeClosed(1,100) //1~100까지의 정수를 가지는 스트림 생성
                 .forEach(i -> {
                     //Member (앤티티) 객체 생성
                     Member member = Member.builder().email("user"+i+"@zerock.com").build();

                     //Board 객체 생성
                     Board board = Board.builder()
                             .title("title" + i)
                             .content("content" + i)
                             .writer(member)
                             .build();

                     //쿼리 실행
                     repository.save(board);
                 });
    }

    //Board테이블의 데이터 select
    @Transactional //@ManyToOne의 fetch가 lazy이기 때문에 디비 연결이 끝기지 않게 @Transactional을 지정해줘야함
    @Test
    public void selectBoard(){

        Optional<Board> result = repository.findById(201L);

        Board board = result.get();

        //쿼리가 따로따로 실행된다
        System.out.println("fetch타입을 lazy로 적용한 결과 = "+board); //Board(bno=201, title=title100, content=content100)
        System.out.println("작성자 = "+board.getWriter()); //Member(email=user100@zerock.com, password=1234, name=user100)

    }

    //특정 게시글 데이터 가져오는 쿼리 메서드(getBoardWithWriter) 테스트
    @Test
    public void getBoardWithWriter(){

        Object result = repository.getBoardWithWriter(201L);

        //한 개의 로우(Object) 내에 Object[]가 들어있기 때문에
        //Object[]로 다운캐스팅 해줘야
        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    //특정 게시글+그 게시글에 달린 댓글 데이터 select 하는 쿼리메서드 테스트
    @Test
    public void getBoardWithReply(){

        List<Object[]> result = repository.getBoardWithReply(201L);

        result.stream().forEach(obj-> System.out.println(Arrays.toString(obj)));
    }

    //목록 페이지에 필요한 데이터 가져오는 쿼리 메서드 테스트
    @Test
    public void getBoardWithReplyCount(){

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<Object[]> result = repository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {

            //row는 object타입이고 이는 obj[]로 되어 있기 때문에 다운캐스팅 필요
            Object[] arr = (Object[]) row;

            //[Board(bno=301, title=title100, content=content100), Member(email=user100@zerock.com, password=1234, name=user100), 1]
            System.out.println(Arrays.toString(arr));
        });
    }

    //특정 게시글 데이터(+댓글갯수)가져오는 쿼리 메서드 테스트
    @Test
    public void getBoardByBno(){

        Object result = repository.getBoardByBno(301L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }





}
