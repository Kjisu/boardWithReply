package org.zerock.boardWithReply.service;


import org.zerock.boardWithReply.dto.ReplyDTO;
import org.zerock.boardWithReply.entity.Board;
import org.zerock.boardWithReply.entity.Reply;

import java.util.List;

public interface ReplyService {

    /////////////////////////////////////////////////////목록
    List<ReplyDTO> getReplyList(Long bno);

    /////////////////////////////////////////////////////등록
    Long register(ReplyDTO dto);

    /////////////////////////////////////////////////////수정
    void modify(ReplyDTO dto);

    /////////////////////////////////////////////////////삭제
    void remove(Long rno);



    //dto -> entity 변환
    default Reply dtoToEntity(ReplyDTO dto){

        Board board = Board.builder().bno(dto.getBno()).build();

        Reply reply = Reply.builder()
                .rno(dto.getRno())
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .board(board)
                .build();

        return reply;

    }

    //entity -> dto 변환
    default ReplyDTO entityToDTO(Reply reply){

        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .build();
        return replyDTO;
    }
}

