package org.zerock.boardWithReply.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.boardWithReply.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    //특정 게시글 조회(댓글 X)
    //Board 엔티티 + Member 엔티티 둘 다 select하는 쿼리 (Board 입장에서 연관관계 있는 경우)
    //근데 이걸 굳이 메서드로 만들 필요가 있을까? 어짜피 @Transactional 과 findById()로 처리할 수 있을 것 같은데
   @Query("select b, w from Board b left join b.writer w where b.bno = :bno ")
    Object getBoardWithWriter(Long bno);


   //Board엔티티와 Reply엔티티 둘 다 select 하는 쿼리 (연관관계 없는 경우)
    @Query("select b, r from Board b LEFT JOIN Reply r ON r.board = b WHERE b.bno =:bno")
    List<Object[]> getBoardWithReply(Long bno);


    //목록 화면에 필요한 데이터 가져오기(게시글번호,제목,작성자,등록일자,댓글갯수)
    //가장 많은 데이터를 가져와야 하는 엔티티 중심으로 조인
    //조인후에는 Board를 기준으로 group by 처리해서 하나의 게시물 당 하나의 라인이 생성될 수 있도록 처리
    @Query(value = "SELECT b,w,count(r) FROM Board b " +
            " LEFT JOIN b.writer w " +
            " LEFT JOIN Reply r " +
            " ON r.board = b " +
            " GROUP BY b",
            countQuery = "SELECT count(b) from Board b ")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);


    //특정 게시글 데이터 가져오기(게시글,회원이름,댓글갯수)
    @Query("SELECT b,w,count(r) " +
            " FROM Board b " +
            " LEFT JOIN b.writer w " +
            " LEFT JOIN Reply r " +
            " ON r.board = b " +
            " WHERE b.bno =:bno")
    Object getBoardByBno(Long bno);


}
