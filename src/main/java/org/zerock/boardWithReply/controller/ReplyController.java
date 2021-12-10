package org.zerock.boardWithReply.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.boardWithReply.dto.ReplyDTO;
import org.zerock.boardWithReply.service.ReplyService;

import java.util.List;

@RestController //작업 후 뷰로 이동하지 않고 http바디에 데이터를 바로 담을꺼니까
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/reply/")
public class ReplyController {

    private final ReplyService service;

    //////////////////////////////////////////////////////////////////////////////////댓글 목록
    @GetMapping(value = "/board/{bno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getReplyList(@PathVariable Long bno){

        log.info(">>>>>>>>>> [C] getReplyList().......");
        log.info("board number is ... "+ bno);

        //service
        List<ReplyDTO> result = service.getReplyList(bno);

        //데이터를 JSON 타입으로 리턴
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //////////////////////////////////////////////////////////////////////////////////등록
    //@RequestBody : http 메세지 바디에 담긴 데이터가 json인 경우 사용 @ModelAttribute는 파라미터 타입일 때 !!
    @PostMapping("/register")
    public ResponseEntity<Long> reRegister(@RequestBody ReplyDTO replyDTO){

        log.info(" >>>>>>>>>> [C] reRgister()......");

        log.info("r>>>"+replyDTO);

        Long rno = service.register(replyDTO);

        return new ResponseEntity<>(rno,HttpStatus.OK);

    }


    ////////////////////////////////////////////////////////////////////////////////// 삭제
    @DeleteMapping("/remove/{rno}")
    public ResponseEntity<String> reRemove(@PathVariable Long rno){

        log.info(">>>>> [C] reRemove().... ");

        service.remove(rno);

        return new ResponseEntity<>("success",HttpStatus.OK);

    }


    ////////////////////////////////////////////////////////////////////////////////// 수정
    @PutMapping("/modify/{rno}")
    public ResponseEntity<String> reModify(@PathVariable Long rno, @RequestBody ReplyDTO dto){

        log.info("[C] reModify()....");

        service.modify(dto);

        return new ResponseEntity<>("success",HttpStatus.OK);

    }

}
