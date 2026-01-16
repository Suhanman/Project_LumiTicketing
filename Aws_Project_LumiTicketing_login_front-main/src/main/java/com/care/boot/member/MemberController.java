package com.care.boot.member;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    @Autowired private MemberService service;  // ✅ 중복 제거
    @Autowired private HttpSession session;




 // ✅ KakaoService 추가

    @RequestMapping("regist")
    public String regist() {
        return "member/regist";
    }

    @PostMapping("registProc")
    public String registProc(MemberDTO member, Model model, RedirectAttributes ra) {
        System.out.println("회원가입 요청 받음: " + member.toString());
        String msg = service.registProc(member);
        System.out.println("회원가입 처리 결과: " + msg);

        if (msg.equals("회원 등록 완료")) {
            ra.addFlashAttribute("msg", msg);

            service.sendWelcomeEmail(member.getEmail(), member.getUserName(), ra);

            return "redirect:index";
        }
        else if(msg.equals("이미 사용중인 아이디입니다.")) {
        	 ra.addFlashAttribute("msg", msg);
             return "redirect:index";
        }
        return "index";
        
       
        
        
    }

    @RequestMapping("login")
    public String login() {
        return "member/login";
    }

    @PostMapping("loginProc")
    public String loginProc(String id, String pw, HttpSession session, HttpServletResponse response) {
        // 서비스에서 판단된 redirect 또는 view 경로 리턴
        return service.loginProc(id, pw, session, response);
    }


    @RequestMapping("test-session")
    public String testSession() {
        return "test-session";  // /jsp/test-session.jsp 를 가리킴
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }
}





