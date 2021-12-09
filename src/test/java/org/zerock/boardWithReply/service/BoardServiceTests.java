package org.zerock.boardWithReply.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.boardWithReply.dto.BoardDTO;
import org.zerock.boardWithReply.dto.PageRequestDTO;
import org.zerock.boardWithReply.dto.PageResultDTO;

import java.util.stream.Stream;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService service;

    //게시글 등록 메서드 테스트
    @Test
    public void register(){

        //given_BoardDTO 객체 생성
        BoardDTO boardDTO = BoardDTO.builder()
                .title("제목 테스트 ")
                .content("내용 테스트")
                .writerEmail("user100@zerock.com")
                .build();

        //when
        Long result = service.register(boardDTO);

        //then
        System.out.println("A number of new thing = "+result);

    }

    //목록 출력 메서드 테스트
    @Test
    public void getList(){

        //given
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        //when
        PageResultDTO<BoardDTO, Object[]> result = service.getList(pageRequestDTO);

        //then
        System.out.println("Total Page count = "+result.getTotalPage());
        Stream.of(result.getDtoList()).forEach(dto-> System.out.println(dto));
    }

    //특정 게시글 불러오는 메서드 테스트
    @Test
    public void getBoard(){

        BoardDTO result = service.getBoard(201L);

        System.out.println("title ... "+ result.getTitle());
    }


    //특정 게시글 삭제 메서드
    @Test
    public void removeWithReplies(){

        service.removeWithReplies(304L);
    }

    //수정 메서드 테스트
    @Test
    public void modify(){

        //given
        BoardDTO boardDTO = BoardDTO.builder().bno(301L).title("수저저정").content("내용숮ㅈ").build();

        service.modify(boardDTO);

    }
}
