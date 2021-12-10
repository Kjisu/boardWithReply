package org.zerock.boardWithReply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {

    //댓글번호,내용,작성자,게시글번호

    private Long rno;
    private String text;
    private String replyer;
    private Long bno;

}
