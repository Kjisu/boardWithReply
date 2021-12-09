package org.zerock.boardWithReply.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO,EN> {

    //다양한 곳에서 사용할 수 있도록 제네릭 타입을 이용해서 DTO,EN 타입 지정
    //제네릭 클래스로 만들면 나중에 어떤 종류의 Page<E>타입이 생성되더라도 PageResultDTO를 이용해서 처리할 수 있다.

    //객체 역할
    //1. JPA처리 결과 Page<엔티티>의 앤티티 객체들 -> dto 타입으로 바꿔줘야 함(데이터 옮기기 작업 필요)
    //2. 뷰단에서 사용할 페이지 정보들을 저장

    private List<DTO> dtoList;//DTO리스트

    private int totalPage; //총 페이지수
    private int page;//현재 페이지 번호
    private int size;//한 페이지 당 게시물 갯수
    private int start,end;//화면에서 시작 페이지 번호, 끝 페이지 번호
    private boolean prev,next; //이전,다음 링크 여부
    private List<Integer> pageList;//페이지 번호 목록


    //생성자_초기화 작업 有
    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn) {

        //Function<EN,DTO> : 엔티티 -> DTO 객체로 변환시켜주는 기능(데이터 옮겨주는 기능)
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        //페이지 관련 데이터(총 페이지수,현재 페이지 번호 등등..) 초기화
        this.totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable){

        //현재 페이지
        this.page = pageable.getPageNumber()+1; //0부터 시작하니까 1을 추가해야 함
        //한 페이지 당 게시물 갯수
        this.size = pageable.getPageSize();
        //시작페이지, 끝 페이지
        int tempEnd = (int)(Math.ceil(page/10.0))*10;//페이지 목록을 1~10까지 11~20..이런식으로 하고 싶으니까 10을 곱한 것 !!
        this.start = tempEnd -9;
        this.end = totalPage>tempEnd? tempEnd:totalPage;
        //prev,next
        this.prev = start>1;
        this.next = totalPage>tempEnd;
        //페이지 번호 목록
        this.pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());

    }
}
