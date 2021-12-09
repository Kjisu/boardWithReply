package org.zerock.boardWithReply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.boardWithReply.entity.Member;

public interface MemberRepository extends JpaRepository<Member,String> {
}
