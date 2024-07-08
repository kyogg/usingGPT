package com.example.usingGPT.Repository;

import com.example.usingGPT.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByUserID(String UserID);
    Member findByNickName(String nickName);
}
