package org.zerock.boardWithReply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.boardWithReply.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    ////////////////////////////////////////////////////////////////게시글 번호가 X인 댓글 지우는 쿼리
    @Query("delete from Reply r where r.board.bno =:bno")
    @Modifying //JPQL을 사용해서 update,delete를 실행하려면 이거 꼭 붙여줘야 함 !!
    void deleteByBno(Long bno);


    ////////////////////////////////////////////////////////////////특정 게시물 번호에 해당되는 댓글 리스트 가져오기
    @Query("select r FROM Reply r where r.board.bno =:bno")
    List<Reply> getReplyList(Long bno);




}
