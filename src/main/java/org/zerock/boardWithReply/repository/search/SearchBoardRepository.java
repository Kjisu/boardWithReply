package org.zerock.boardWithReply.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.boardWithReply.entity.Board;

public interface SearchBoardRepository {

    //테스트용
    Board search1();

    //검색 유형,키워드,페이지 객체를 매개변수로 받아 작업 처리
    //PageRequestDTO로 유형과 키워드를 받지 않는 이유는 DTO를 가능하면 Repository영역에서 다루지 않게 하기 위함
    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
