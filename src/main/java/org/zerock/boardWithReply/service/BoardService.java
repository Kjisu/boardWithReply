package org.zerock.boardWithReply.service;

import org.zerock.boardWithReply.dto.BoardDTO;
import org.zerock.boardWithReply.dto.PageRequestDTO;
import org.zerock.boardWithReply.dto.PageResultDTO;
import org.zerock.boardWithReply.entity.Board;
import org.zerock.boardWithReply.entity.Member;


public interface BoardService {

    ///////////////////////////////////////////////////// 게시글 등록 메서드
    Long register(BoardDTO dto);

    ////////////////////////////////////////////////////// 목록 출력 메서드
    PageResultDTO<BoardDTO,Object[]> getList(PageRequestDTO dto);

    /////////////////////////////////////////////////////// 조회 작업
    BoardDTO getBoard(Long bno);

    /////////////////////////////////////////////////////// 삭제 작업
    void removeWithReplies(Long bno);

    /////////////////////////////////////////////////////// 수정 작업
    void modify(BoardDTO dto);

    ////////////////////////////////////////////// dto->entity로 변환 (모든 구현체가 사용할 수 있도록 해야 하기 때문에 default메서드로 작성)
    default Board dtoToEntity(BoardDTO dto){

        //Member엔티티 객체 생성
        Member member = Member.builder()
                .email(dto.getWriterEmail())
                .build();

        //Board엔티티 객체 생성
        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

        return board;
    }

    ////////////////////////////////////////////////////////////////////////////////////// entity -> dto
    default BoardDTO entityToDTO(Board board,Member member,Long replyCount){

        //BoardDTO 객체 생성
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writerEmail(member.getEmail())
                .modDate(board.getModDate())
                .regDate(board.getRegDate())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();

        return boardDTO;
    }
}
