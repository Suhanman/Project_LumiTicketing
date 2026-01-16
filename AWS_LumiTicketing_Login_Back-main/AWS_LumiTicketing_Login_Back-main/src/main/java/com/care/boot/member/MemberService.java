package com.care.boot.member;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.care.boot.config.RedisService;

@Service


public class MemberService {
    @Autowired private IMemberMapper mapper;
    @Autowired private HttpSession session;
    @Autowired private HttpServletResponse response;
    @Autowired private RedisService redisService;

    public String registProc(MemberDTO member) {
        if(member.getId() == null || member.getId().trim().isEmpty()) return "아이디를 입력하세요.";
        if(member.getPw() == null || member.getPw().trim().isEmpty()) return "비밀번호를 입력하세요.";
        if(!member.getPw().equals(member.getConfirm())) return "두 비밀번호를 일치하여 입력하세요.";
        if(member.getUserName() == null || member.getUserName().trim().isEmpty()) return "이름을 입력하세요.";

        MemberDTO check = mapper.login(member.getId());
        if(check != null) return "이미 사용중인 아이디입니다.";

        /* 비밀번호 암호화 */
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secretPass = encoder.encode(member.getPw());
        member.setPw(secretPass);

        int result = mapper.registProc(member);
        return (result == 1) ? "회원 등록 완료" : "회원 등록을 다시 시도하세요.";
    }
    


 

    
    public String loginProc(String id, String pw, HttpSession session, HttpServletResponse response) {
        if (id == null || id.trim().isEmpty()) return "아이디를 입력하세요.";
        if (pw == null || pw.trim().isEmpty()) return "비밀번호를 입력하세요.";

        MemberDTO check = mapper.login(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (check != null && encoder.matches(pw, check.getPw())) {
            session.setAttribute("id", check.getId());
            session.setAttribute("userName", check.getUserName());
            session.setAttribute("mobile", check.getMobile());
            session.setAttribute("membership", check.getMembership());

            // ✅ loginUser 세션에 MemberDTO 객체 통째로 저장
            session.setAttribute("loginUser", check);

            String grade = check.getMembership();
            try {
                redisService.save(check.getId(), new ObjectMapper().writeValueAsString(check));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            if ("vip".equalsIgnoreCase(grade) || "admin".equalsIgnoreCase(grade)) {
                response.setHeader("X-User-Membership", "vip");
                return "redirect:https://vip.lumiticketing.click/boot/index";
            }

            response.setHeader("X-User-Membership", "regular");
            return "redirect:https://www.lumiticketing.click/boot/index";
        }

        return "아이디 또는 비밀번호를 확인 후 다시 입력하세요.";
    }


   
    

    
    
	}
    
