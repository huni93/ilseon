package com.com.com.Aproject.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.com.com.Aproject.dto.ABoard;
import com.com.com.Aproject.dto.AMember;
import com.com.com.Aproject.service.AService;

@Controller
public class MainController {

    @Autowired
    private AService aService;

    @RequestMapping(value = "/Aproject/main", method = RequestMethod.GET)
    public String mainPage(HttpSession session, Model model) {
        AMember member = (AMember) session.getAttribute("member");
        
        if (member == null) {
            return "redirect:/login";
        }
        


        String memberId = member.getMemberId();
        String memberName = member.getMemberName();
        String memberRank = member.getMemberRank();
        
        
        
        Date proxyDate = (Date) session.getAttribute("proxyDate"); // 세션에서 proxyDate 가져오기
        
        
        System.out.println(member.getMemberRank());

        if (memberRank.equals("004")) {
            // 부장일 경우 결재중인 글 목록만 가져옴
            List<ABoard> approvalPendingBoards = aService.getApprovalPendingBoards();
            model.addAttribute("approvalPendingBoards", approvalPendingBoards);
        } else {
            // 그 외의 경우 일반 게시글 목록 가져옴
            List<ABoard> boardList = aService.getBoardsByMemberId(memberId, memberName, memberRank, proxyDate);
            model.addAttribute("boardList", boardList);
        }
       

        model.addAttribute("member", member);
        return "Aproject/main";
    }




    // 추가: 결재 처리 요청 처리
    @Transactional
    @RequestMapping(value = "/processApproval", method = RequestMethod.POST)
    public String processApproval(int seq, HttpSession session) {
        AMember member = (AMember) session.getAttribute("member");
        if (member != null && ("003".equals(member.getMemberRank()) || "004".equals(member.getMemberRank()))) {
            // 과장이나 부장인 경우에 처리
            ABoard board = aService.getBoard(seq);
            if (board != null) {
                if ("003".equals(member.getMemberRank())) {
                    board.setApprstat("03"); // 과장인 경우 결재 상태를 '03'으로 업데이트
                } else if ("004".equals(member.getMemberRank())) {
                    board.setApprstat("04"); // 부장인 경우 결재 상태를 '04'으로 업데이트
                }
               
                aService.updateBoard(board);
            }
        }
        return "redirect:/Aproject/main";
    }
    
    @RequestMapping(value = "/AsearchBoard", method = RequestMethod.GET)
    public String AsearchBoard(@RequestParam("searchType") String searchType,
                              @RequestParam("searchKeyword") String searchKeyword,
                              @RequestParam(value = "startDate", required = false) String startDate,
                              @RequestParam(value = "endDate", required = false) String endDate,
                              HttpSession session, Model model) {
        System.out.println("Search Parameters: " + searchType + ", " + searchKeyword + ", " + startDate + ", " + endDate);
        
        AMember member = (AMember) session.getAttribute("member");
        String memberId = member.getMemberId();
        String memberRank = member.getMemberRank();
        String memberName = member.getMemberName();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", memberId);
        params.put("searchType", searchType);
        params.put("searchKeyword", searchKeyword);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("memberRank", memberRank);
        params.put("memberName", memberName);

        List<ABoard> list = aService.AsearchBoard(params);
        System.out.println("Search Results: " + list);
        
        model.addAttribute("boardList", list);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        
        return "Aproject/main";
    }


 // ajax
    @RequestMapping(value = "/getApprovalStatuses", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> getApprovalStatuses() {
        List<Map<String, String>> statuses = new ArrayList<Map<String, String>>();

        statuses.add(createStatus("01", "임시저장"));
        statuses.add(createStatus("02", "결재대기"));
        statuses.add(createStatus("03", "결재중"));
        statuses.add(createStatus("04", "결재완료"));
        statuses.add(createStatus("05", "반려"));

        return statuses;
    }

    private Map<String, String> createStatus(String value, String label) {
        Map<String, String> status = new HashMap<String, String>();
        status.put("value", value);
        status.put("label", label);
        return status;
    }

    @RequestMapping(value = "/filterBoardsByApprovalStatus", method = RequestMethod.GET)
    @ResponseBody
    public List<ABoard> filterBoardsByApprovalStatus(@RequestParam("approvalStatus") String approvalStatus,
                                                     @RequestParam("searchType") String searchType,
                                                     @RequestParam("searchKeyword") String searchKeyword,
                                                     @RequestParam(value = "startDate", required = false) String startDate,
                                                     @RequestParam(value = "endDate", required = false) String endDate,
                                                     HttpServletRequest request) {
        HttpSession session = request.getSession();
        AMember member = (AMember) session.getAttribute("member");
        
        // 세션에서 memberId 가져오기
        String memberId = member != null ? member.getMemberId() : null;
        String memberRank = member != null ? member.getMemberRank() : null;
        String memberName = member != null ? member.getMemberName() : null;

        List<ABoard> filteredBoards = new ArrayList<ABoard>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", memberId);
        params.put("approvalStatus", approvalStatus);
        params.put("searchType", searchType);
        params.put("searchKeyword", searchKeyword);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("memberRank", memberRank);
        params.put("memberName", memberName);

        if ("all".equals(approvalStatus)) {
            // 모든 상태의 게시글 가져오기
            filteredBoards = aService.AsearchBoard(params);
        } else {
            // 특정 결재 상태에 해당하는 게시글 필터링
            filteredBoards = aService.getBoardsByApprovalStatus(params);
        }
        
        return filteredBoards;
    }
}
