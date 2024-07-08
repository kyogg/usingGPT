package com.example.usingGPT.Service;

import com.example.usingGPT.Entity.Member;
import com.example.usingGPT.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public boolean checkUserID(String userID) {
        if (memberRepository.findByUserID(userID) != null)
            return true;
        else
            throw new IllegalArgumentException("Duplicated userID");
    }

    public boolean checkNickName(String nickName) {
        if (memberRepository.findByNickName(nickName) != null)
            return true;
        else
            throw new IllegalArgumentException("Duplicated nickname");
    }
}
