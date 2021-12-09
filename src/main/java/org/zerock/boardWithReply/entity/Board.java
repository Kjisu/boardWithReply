package org.zerock.boardWithReply.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString(exclude = "writer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BaseEntity{

    //연관관계(fk)가 필요한 컬럼은 기본 컬럼들을 다 작성한 후 맨 나중에 건들일 것 !

    //게시글번호(pk),제목,내용,작성자(fk)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    private String title;
    private String content;
    //작성자(fk)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;
    //테이블 생성하니 컬럼명으로 writer가 아닌 "writer_email"로 생성됨
    //"변수명_연관관계 맺고 있는 테이블의 pk컬럼명"

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
