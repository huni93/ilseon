package com.com.com.Aproject.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.com.com.Aproject.dto.ABoard;
import com.com.com.Aproject.dto.AHistory;
import com.com.com.Aproject.dto.AMember;
import com.com.com.Aproject.service.AService;

@Controller
public class WriteController {

    @Autowired
    private AService aService;

    @RequestMapping(value = "/awrite", method = RequestMethod.GET)
    public String boardForm(@RequestParam(value = "seq", required = false) Integer seq, HttpSession session, Model model) {
        AMember member = (AMember) session.getAttribute("member");
        if (member != null) {
            model.addAttribute("memberName", member.getMemberName());
            model.addAttribute("memberRank", member.getMemberRank());
            if (seq != null) {
                ABoard board = aService.getBoard(seq);
                if (board != null) {
                    
                    model.addAttribute("board", board);
                    model.addAttribute("mode", "update");
                              
                    List<AHistory> historyList = aService.getHistoryByHiNo(seq);
                    model.addAttribute("historyList", historyList);
                    
                    // 히스토리에서 가장 최근 항목의 상태를 기준으로 approvalStatus 설정
                    if (!historyList.isEmpty()) {
                        AHistory latestHistory = historyList.get(0); // 가장 최근 항목이 첫 번째에 있다고 가정
                        model.addAttribute("approvalStatus", latestHistory.getSignStatus());
                    } else {
                        model.addAttribute("approvalStatus", board.getApprstat());
                    }
                    
                    if ("02".equals(board.getApprstat())) {
                        model.addAttribute("isApprovalPending", true);
                        model.addAttribute("isRejected", false);
                    } else if ("05".equals(board.getApprstat())) {
                        model.addAttribute("isApprovalPending", false);
                        model.addAttribute("isRejected", true);
                    } else {
                        model.addAttribute("isApprovalPending", false);
                        model.addAttribute("isRejected", false);
                    }

                    boolean isAuthor = member.getMemberId().equals(board.getMemberId());
                    model.addAttribute("isAuthor", isAuthor);
                    
                    return "Aproject/awrite";
                } else {
                    return "redirect:/Aproject/main";
                }
            } else {
                ABoard newBoard = new ABoard();
                int lastSeq = aService.getLastBoardSeq();
                newBoard.setSeq(lastSeq + 1);
                model.addAttribute("board", newBoard);
                model.addAttribute("mode", "create");
                model.addAttribute("isApprovalPending", false);
                model.addAttribute("isRejected", false);
                return "Aproject/awrite";
            }
        } else {
            return "redirect:/login";
        }
    }

    @Transactional
    @RequestMapping(value = "/saveBoard", method = RequestMethod.POST)
    public String saveBoard(@ModelAttribute("board") ABoard board,
                            @RequestParam("submit_type") String submitType,
                            HttpSession session, Model model) {
        System.out.println(board.getApprstat());
        System.out.println(submitType);
        AMember member = (AMember) session.getAttribute("member");
        if (member != null) {
            board.setMemberId(member.getMemberId());

            // submitType에 따라 상태 설정
            if ("임시저장".equals(submitType)) {
                board.setApprstat("01"); // 임시저장일 경우 상태를 "01"로 설정
            } else if ("결재".equals(submitType)) {
            	handleApproval(board, member, submitType); // 결재 버튼 클릭 시 상태 처리
                System.out.println("결재처리 stat"+board.getApprstat());
            } else if ("반려".equals(submitType)) {
                board.setApprstat("05");
            }        
            System.out.println(board.getBoardTitle());
            
         // 새 글 작성 로직에 진입하기 전 seq 값 확인
            System.out.println("Board seq before processing: " + board.getSeq());

            int lastSeq = aService.getLastBoardSeq();

         // 기존 글인지 새로운 글인지 판단
            ABoard existingBoard = aService.getBoard(board.getSeq());
            if (existingBoard != null) {
                // 기존 글 수정 로직
            	if (!"01".equals(board.getApprstat()) && !"02".equals(board.getApprstat())) {  // 결재 상태가 임시저장이 아닌 경우에만 결재자와 날짜 업데이트
                    board.setApprDate(new Date());
                    board.setApprover(member.getMemberId());
                }
                System.out.println("수정 로직 진행");
                aService.updateBoardWithApproval(board);
            } else {
                // 새 글 등록 로직
                board.setSeq(lastSeq + 1);
                System.out.println("새로운 글 등록 로직 진행");
                aService.saveBoard(board);
            }
            System.out.println("처리 완료");
            // 히스토리 저장
            AHistory history = new AHistory();
            history.setHiNo(board.getSeq());
            history.setSignDate(new Date());
            history.setApproval(member.getMemberId());
            history.setSignStatus(board.getApprstat());
            aService.insertHistory(history);
            
          

            return "redirect:/Aproject/main";
        } else {
            model.addAttribute("error", "로그인이 필요합니다.");
            return "redirect:/login";
        }
    }




    private void handleApproval(ABoard board, AMember member, String submitType) {
        // 상태 초기 설정
        if (board.getApprstat() == null) {
            board.setApprstat("01");  // 기본적으로 임시저장으로 설정
        }

        if ("임시저장".equals(submitType)) {
            board.setApprstat("01");
        } else if ("결재".equals(submitType)) {
            if (member.getMemberRank().equals("001") || member.getMemberRank().equals("002")) {
                board.setApprstat("02");
            } else if (member.getMemberRank().equals("003")) {
                board.setApprstat("03");
            } else if (member.getMemberRank().equals("004")) {
                board.setApprstat("04");
            }
        } else if ("반려".equals(submitType)) {
            board.setApprstat("05");
        }
    }
        }
    