package org.zerock.boardWithReply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    //화면으로부터 전달받거나 전달할 데이터 담고 있는 클래스
    //게시글 번호,제목,내용,작성자,작성자이름,등록일,수정일,댓글갯수

    private Long bno;
    private String title;
    private String content;
    private String writerEmail;
    private String writerName;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private int replyCount;


}
