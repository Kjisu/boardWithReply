package org.zerock.boardWithReply.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.boardWithReply.dto.ReplyDTO;
import org.zerock.boardWithReply.entity.Reply;
import org.zerock.boardWithReply.repository.ReplyRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository repository;

    /////////////////////////////////////////////////////////// 목록
    @Override
    public List<ReplyDTO> getReplyList(Long bno) {

        log.info(">>>>>> [S] getReplyList() ....... ");

        //디비 실행
        List<Reply> result = repository.getReplyList(bno);

        //entity -> dto로 변환
        return result.stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());

    }

    /////////////////////////////////////////////////////////// 등록
    @Override
    public Long register(ReplyDTO dto) {

        log.info("[S] reReister().......");

        //dto -> entity로 변환
        Reply reply = dtoToEntity(dto);

        Reply result = repository.save(reply);

        return result.getRno();
    }


    /////////////////////////////////////////////////////////// 수정
    @Override
    public void modify(ReplyDTO dto) {

        log.info("[S] reModify()......");

        //dto->entity
        Reply reply = dtoToEntity(dto);

        repository.save(reply);
    }


    /////////////////////////////////////////////////////////// 삭제
    @Override
    public void remove(Long rno) {

        log.info("[S] reRemove()....");

        repository.deleteById(rno);

    }
}
