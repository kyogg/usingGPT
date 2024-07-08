package com.example.usingGPT.Controller;

import com.example.usingGPT.Entity.Member;
import com.example.usingGPT.Repository.MemberRepository;
import com.example.usingGPT.Service.MemberService;
import com.example.usingGPT.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    //회원 가입
    @ResponseBody
    @PostMapping("/new")
    public Member join(@RequestBody MemberDto request) {
        Member member = new Member();
        String userID = request.getUserID();
        String password = request.getPassword();

        if (memberService.checkUserID(userID))
            member.setUserID(userID);
        member.setPassword(password);

        memberService.save(member);

        return member;
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberDto request, HttpServletRequest sessionRequest) {
        String userID = request.getUserID();
        String password = request.getPassword();

        if (userID == null || password == null) {
            return ResponseEntity.badRequest().body("사용자ID 또는 password가 빈칸입니다.");
        }

        if (memberRepository.findByUserID(userID) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자ID를 찾을 수 없습니다.");
        }
        else if (memberRepository.findByUserID(userID).getPassword() != password) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 틀립니다.");
        }

        HttpSession session = sessionRequest.getSession();
        session.setAttribute("userID", userID);

        return ResponseEntity.ok("로그인 성공");
    }

    //닉네임 설정
    @ResponseBody
    @PostMapping("/setNickName")
    public ResponseEntity<String> setNickName(@RequestBody String nickName, HttpServletRequest sessionRequest) {
        HttpSession session = sessionRequest.getSession(false);

        if (session == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 상태가 아닙니다.");

        String userID = (String) session.getAttribute("userID");

        if (userID == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션에 사용자 ID가 없습니다.");

        Member member = memberRepository.findByUserID(userID);

        if (member == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");

        member.setNickName(nickName);
        memberRepository.save(member);

        return ResponseEntity.ok("닉네임 설정 성공");
    }

    //로그아웃
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest sessionRequest) {
        HttpSession session = sessionRequest.getSession(false);

        if (session == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 상태가 아닙니다.");

        session.invalidate();
        return ResponseEntity.ok("로그아웃 성공");
    }
}
