package com.com.com.Aproject.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.com.com.Aproject.dto.AMember;
import com.com.com.Aproject.dto.AProxy;
import com.com.com.Aproject.service.AService;

@Controller
public class ProxyController {

    @Autowired
    private AService aService;

    @RequestMapping(value = "/proxyForm", method = RequestMethod.GET)
    public String proxyForm(Model model, HttpSession session) {
    	
    	AMember member = (AMember) session.getAttribute("member");
        // 세션에서 사용자 정보 가져오기
    	
    	List<AProxy> proxies = aService.selectProxiesByRank(member.getMemberRank());
        model.addAttribute("selectproxy", proxies);
    	
        String memberName = member.getMemberName();
        String memberRank = member.getMemberRank();

        // 모델에 사용자 정보 추가
        model.addAttribute("memberName", memberName);
        model.addAttribute("memberRank", memberRank);

        return "Aproject/proxyForm";
    }
    
    

    @RequestMapping(value = "/updateProxy", method = RequestMethod.POST)
    public String updateProxyAuthorization(HttpSession session, @RequestParam("proxyId") String proxyId) {
        AMember member = (AMember) session.getAttribute("member");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", member.getMemberId());
        params.put("memberRank", member.getMemberRank());
        params.put("proxyId", proxyId);

        aService.updateProxy(params);

        return "redirect:/Aproject/main"; 
    }
}
