package org.zerock.boardWithReply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
public class PageRequestDTO {

    //객체 역할
    //1. 페이징 처리할 때 필요한 데이터가 파라미터형태로 넘어오는데 이들을 저장
    //뷰단에서 전달되는 page,size 파라미터를 수집하여 담는 상자
    //2. JPA에서 사용할 Pageable타입의 객체를 생성

    private int page;//현재 페이지 번호
    private int size;//한 페이지당 출력할 게시글 갯수
    private String type;
    private String keyword;

    //생성자_초기화 작업 必
    public PageRequestDTO() {
        //제일 처음 list페이지 요청될 때는, 페이지 정보가 없는 상태에서 요청되니까 초기화 작업 필요
        this.page = 1;
        this.size = 10;
    }

    //Pageable 객체 생성
    public Pageable getPageable(Sort sort){

        return PageRequest.of(page-1,size,sort);
    }


}
