package org.zerock.boardWithReply.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString(exclude = "board")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reply extends BaseEntity{

    //연관관계(fk)가 필요한 컬럼은 기본 컬럼들을 다 작성한 후 맨 나중에 건들일 것 !

    //댓글번호(pk),내용,작성자(비회원 작성 가능_전체 프로젝트 만들 때는 회원만 작성 가능하도록 해보자 _ fk),게시글번호(fk)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;

    private String replyer;

    //게시글번호
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
    //테이블 생성하니 컬럼명으로 writer가 아닌 "board_bno"로 생성됨
    //"변수명_연관관계 맺고 있는 테이블의 pk컬럼명"


}
