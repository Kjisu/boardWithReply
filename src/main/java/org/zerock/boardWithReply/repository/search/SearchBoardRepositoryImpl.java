package org.zerock.boardWithReply.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.boardWithReply.entity.Board;
import org.zerock.boardWithReply.entity.QBoard;
import org.zerock.boardWithReply.entity.QMember;
import org.zerock.boardWithReply.entity.QReply;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{


    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }
    ///////////////////////////////////////////////////////////////////////////////////// 테스트
    @Override
    public Board search1() {

        log.info("search1()................");

        //필요한 q도메인 객체 생성
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        //JPQLQuery 객체 생성
        JPQLQuery<Board> jpqlQuery = from(board);

        //JPQL 쿼리 작성
        jpqlQuery.leftJoin(member).on(board.writer.eq(member))
                .leftJoin(reply).on(reply.board.eq(board));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
        tuple.groupBy(board);

        log.info("작성한 쿼리 = "+tuple);
        log.info("-----------------------------------------");

        //쿼리 실행
        List<Tuple> result = tuple.fetch();

        log.info(result);
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        log.info("searchPage()........" );

        //필요한 도매인 객체 생성
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        //쿼리 작성을 위한 JPQLQuery 객체 생성
        JPQLQuery<Board> jpqlQuery = from(board);

        //쿼리 작성
        jpqlQuery.leftJoin(member).on(board.writer.eq(member))
                .leftJoin(reply).on(reply.board.eq(board));
        //select할 것들이 객체 단위가 아니라 각각의 데이터를 추출하는 경우 -> Tuple 객체 이용
        JPQLQuery<Tuple> tupleJPQLQuery = jpqlQuery.select(board, member, reply.count());
        //where 조건절  -> BooleanBuiler객체 사용하여 작성
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        //조건문1
        booleanBuilder.and(board.bno.gt(1L));
        //조건문2 - 상세검색조건문이 있다면
        if(type != null){
            String[] typeArr = type.split("");//tw 인 경우 두 곳에서 모두 검색될 수 있도록 공백 자름
            //검색 조건문 작성
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for(String c:typeArr){
                switch (c){
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        //tuple에 만든 where문 붙이기
        tupleJPQLQuery.where(booleanBuilder);

        //order by 정렬 쿼리 문장 만들기
        //Pageable객체에 있는 Sort 정보 꺼내서
        Sort sort = pageable.getSort();
        //tuple.orderBy(board.bno.desc());//이게 안 되니까 이걸 풀어서 만들기
        sort.stream().forEach( o ->{
            //꺼낸 Sort 정보가 asc인지 desc인지 확인
            Order direction = o.isAscending()? Order.ASC:Order.DESC;
            String property = o.getProperty();//정렬 기준이 되는 컬럼 이름
            PathBuilder ordeByExpression = new PathBuilder(Board.class, "board");
            tupleJPQLQuery.orderBy(new OrderSpecifier(direction,ordeByExpression.get(property)));
        });

        //그룹핑 쿼리는 맨 나중에 작성
        tupleJPQLQuery.groupBy(board);

        //page처리 쿼리
        tupleJPQLQuery.offset(pageable.getOffset());//0
        tupleJPQLQuery.limit(pageable.getPageSize());//10

        //드뎌 쿼리 실행 ..
        List<Tuple> result = tupleJPQLQuery.fetch();

        log.info(result);

        //
        long count = tupleJPQLQuery.fetchCount();
        log.info("count = "+count);

        //Page<Object[]>의 반환타입을 만들기 위해 Page인터페이스의 구현체인 PageImpl 클래스를 생성한다
        return new PageImpl<Object[]>(
                result.stream().map(t-> t.toArray()).collect(Collectors.toList()),
                pageable,
                count);
    }
}
