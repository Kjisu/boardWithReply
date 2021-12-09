package org.zerock.boardWithReply.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.boardWithReply.dto.BoardDTO;
import org.zerock.boardWithReply.dto.PageRequestDTO;
import org.zerock.boardWithReply.dto.PageResultDTO;
import org.zerock.boardWithReply.service.BoardService;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board/")
public class BoardController {

    private final BoardService service;

    //목록 페이지 요청
    @GetMapping("/list")
    public void list(@ModelAttribute PageRequestDTO requestDTO, Model model){

        log.info(">>>>>>>>>>> [C] list()......");

        //service
        PageResultDTO<BoardDTO, Object[]> result = service.getList(requestDTO);

        //모델 영역에 결과값 저장
        model.addAttribute("result",result);

    }

    //등록 페이지 요청
    @GetMapping("/register")
    public void register(){

        log.info(">>>>>>>>>>>>>>> [C] register()...GET....");


    }

    //게시글 등록 처리
    @PostMapping("/register")
    public String registerPost(@ModelAttribute BoardDTO dto, RedirectAttributes redirectAttributes){
        log.info(">>>>>>>>>>>>>>> [C] register()...POST....");

        //service
        Long result = service.register(dto);

        //목록 페이지로 이동
        redirectAttributes.addFlashAttribute("bno",result);

        return "redirect:/board/list";

    }

    //조회,수정 페이지 요청
    @GetMapping({"/read","modify"})
    public void read(@ModelAttribute PageRequestDTO requestDTO, @RequestParam Long bno,Model model){
        log.info(">>>>>>>>>>>>>> [C] read()...GET");

        //service
        BoardDTO result = service.getBoard(bno);

        //model 영역에 특정 게시글 데이터 저장
        model.addAttribute("dto",result);
    }

    //수정 처리 요청
    @PostMapping("/modify")
    public String modify(@ModelAttribute BoardDTO dto,PageRequestDTO pageRequestDTO,RedirectAttributes redirectAttributes){
        log.info(">>>>>>>>>>>>>> [C] modify()....POST");

        //service
        service.modify(dto);

        //목록 페이징에 필요한 데이터 저장
        redirectAttributes.addAttribute("bno",dto.getBno());
        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("type",pageRequestDTO.getType());
        redirectAttributes.addAttribute("keyword",pageRequestDTO.getKeyword());

        return "redirect:/board/read";
    }

    //삭제 요청 처리
    @PostMapping("/remove")
    public String remove(@RequestParam Long bno,RedirectAttributes redirectAttributes){

        log.info(">>>>>>>>>>>>>> [C] remove()....POST");

        //service
        service.removeWithReplies(bno);

        //삭제한 게시글 번호 저장
        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board/list";
    }

}
