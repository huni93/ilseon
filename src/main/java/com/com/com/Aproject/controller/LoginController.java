package com.com.com.Aproject.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.com.com.Aproject.dto.AMember;
import com.com.com.Aproject.dto.AProxy;
import com.com.com.Aproject.service.AService;

@Controller
public class LoginController {

    @Autowired
    private AService aService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Model model) {
        return "Aproject/login"; // 로그인 페이지로 이동
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginProc(AMember member, HttpSession session, RedirectAttributes redirectAttributes) {
    	
    	 // 로그인 처리 전에 오래된 프록시를 되돌림
        aService.revertProxy();
    	
    	
        String msg = null;
        // 아이디와 비밀번호 빈칸 체크
        if (member.getMemberId() == null || member.getMemberId().trim().isEmpty()) {
            msg = "아이디를 입력해주세요.";
            redirectAttributes.addFlashAttribute("msg", msg);
            return "redirect:/login";
        }

        if (member.getMemberPw() == null || member.getMemberPw().trim().isEmpty()) {
            msg = "비밀번호를 입력해주세요.";
            redirectAttributes.addFlashAttribute("msg", msg);
            return "redirect:/login";
        }

        // 아이디 체크
        int idRe = aService.idCheck(member.getMemberId());
        if (idRe == 0) {
            msg = "해당하는 아이디가 없습니다.";
            redirectAttributes.addFlashAttribute("msg", msg);
            return "redirect:/login";
        }
     

     // 로그인 시도
        AMember loggedInMember = aService.login(member);
        if (loggedInMember != null) {
            session.setAttribute("member", loggedInMember);
            msg = "안녕하세요 " + loggedInMember.getMemberName() + "님";

            // 대리 결재 권한 확인
            checkAndSetProxyInfo(loggedInMember, session);
            
         // 대리 결재 권한 확인 및 세션에 proxyDate 저장
            Date proxyDate = checkAndSetProxyInfo(loggedInMember, session);
            if (proxyDate != null) {
                session.setAttribute("proxyDate", proxyDate);
            }
            
         
            session.setAttribute("member", loggedInMember);
            redirectAttributes.addFlashAttribute("msg", "환영합니다, " + member.getMemberName() + "님!");
            
            return "redirect:/Aproject/main"; // 로그인 성공 시 이동할 페이지
        } else {
            msg = "비밀번호가 틀렸습니다.";
            redirectAttributes.addFlashAttribute("msg", msg);
            return "redirect:/login"; // 로그인 실패 시 다시 로그인 페이지로
        }
    }
    private Date checkAndSetProxyInfo(AMember member, HttpSession session) {
        AProxy proxyInfo = aService.getProxyInfoByMemberId(member.getMemberId());
        if (proxyInfo != null && !"N".equals(proxyInfo.getProxyRank())) {
            member.setFirstRank(member.getMemberRank()); // 기존 등급 저장
            member.setMemberRank(proxyInfo.getProxyRank()); // 대리 결재자 등급으로 변경
            member.setApperId(proxyInfo.getApperId()); // 대리 결재자 ID 저장
            session.setAttribute("proxyInfo", proxyInfo); // 세션에 대리 결재자 정보 저장
            
            // 세션에 변경된 member 정보 업데이트
            session.setAttribute("member", member);
            
            System.out.println(member);
            
            System.out.println("기존등급" + member.getFirstRank());
            System.out.println("바뀐등급" + member.getMemberRank());
            
            
            return proxyInfo.getProxyDate();
        }
        return null; // 대리 권한이 없거나 유효하지 않을 때
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        // 세션을 무효화하여 로그아웃 처리
        session.invalidate();
//        session.removeAttribute("userInfo");
        // 로그인 페이지로 리다이렉트
        return "redirect:/login";
    }
}
