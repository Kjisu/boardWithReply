package org.zerock.boardWithReply.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.boardWithReply.dto.BoardDTO;
import org.zerock.boardWithReply.dto.PageRequestDTO;
import org.zerock.boardWithReply.dto.PageResultDTO;
import org.zerock.boardWithReply.entity.Board;
import org.zerock.boardWithReply.entity.Member;
import org.zerock.boardWithReply.repository.BoardRepository;
import org.zerock.boardWithReply.repository.ReplyRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository repository;
    private final ReplyRepository replyRepository;

    ///////////////////////////////////////////////////////////////////////// 등록
    @Override
    public Long register(BoardDTO dto) {

        log.info(" >>>>>>>>>>>> [S] register()...");

        //dto -> entity 로 변환
        Board board = dtoToEntity(dto);

        //쿼리실행
        Board result = repository.save(board);

        return result.getBno();
    }

    ///////////////////////////////////////////////////////////////////////// 목록
    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO dto) {

        log.info(">>>>>>>> [S] getList()...");

        //쿼리실행
        //Page<Object[]> result = repository.getBoardWithReplyCount(dto.getPageable(Sort.by("bno").descending()));
        Page<Object[]> result = repository.searchPage(dto.getType(), dto.getKeyword(), dto.getPageable(Sort.by("bno").descending()));

        //entity->dto로 변환하는 기능 Function인터페이스에 담기
        Function<Object[],BoardDTO> fn = (en -> entityToDTO((Board) en[0],(Member) en[1],(Long) en[2]));

        return new PageResultDTO<>(result,fn);
    }

    ///////////////////////////////////////////////////////////////////////// 조회
    @Override
    public BoardDTO getBoard(Long bno) {

        log.info(">>>>>>>[S] getBoard()");

        //쿼리실행
        Object result = repository.getBoardByBno(bno);

        //다운캐스팅
        Object[] arr = (Object[]) result;

        //dto로 변환
        BoardDTO boardDTO = entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]);

        return boardDTO;
    }



    ///////////////////////////////////////////////////////////////////////// 삭제
    @Override
    @Transactional //쿼리 2번 실행해야 하니까 ,, 하나의 쿼리 실행 후 디비 연결이 끈기지 않도록 ...
    public void removeWithReplies(Long bno) {

        log.info(">>>>>>>>>>> [S] removeWithReplies().....");

        //댓글 먼저 지우고
        replyRepository.deleteByBno(bno);

        //게시글 삭제
        repository.deleteById(bno);
    }

    ///////////////////////////////////////////////////////////////////////// 수정
    @Override
    public void modify(BoardDTO dto) {

        log.info(">>>>>>>>>>>>>[S] modify()...... ");

        //수정할 엔티티 불러오기
        Optional<Board> result = repository.findById(dto.getBno());

        if(result.isPresent()){
            Board board = result.get();

            board.changeTitle(dto.getTitle());
            board.changeContent(dto.getContent());

            repository.save(board);
        }
    }
}
