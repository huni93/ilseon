package com.com.com;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.com.com.dto.Board;
import com.com.com.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public String list(Board board, Model model) {
		List<Board> list = boardService.list(board);
		model.addAttribute("boardList", list);
		return "board";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String showWriteForm(@RequestParam(value = "seq", required = false) Long seq, Model model) {
		if (seq != null) {
			boardService.increaseViewCount(seq); // 조회수 증가
			Board board = boardService.getBoard(seq);
			model.addAttribute("board", board);
			model.addAttribute("mode", "update");
		} else {
			model.addAttribute("board", new Board());
			model.addAttribute("mode", "create");
		}
		return "write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute("board") Board board, @RequestParam("mode") String mode, Model model) {
		if ("update".equals(mode)) {
			boardService.updateBoard(board);
			model.addAttribute("message", "게시글이 성공적으로 수정되었습니다.");
		} else {
			boardService.insertBoard(board);
			model.addAttribute("message", "게시글이 성공적으로 등록되었습니다.");
		}
		return "redirect:/board";
	}

	@RequestMapping(value = "/deleteBoard", method = RequestMethod.POST)
	public String deleteBoard(@ModelAttribute("seqList") String seqList, Model model) {
		String[] seqArray = seqList.split(",");
		for (String seq : seqArray) {
			boardService.deleteBoard(Long.parseLong(seq));
		}
		model.addAttribute("message", "선택한 게시글들이 삭제되었습니다.");
		return "redirect:/board";
	}
	
	@RequestMapping(value = "/searchBoard", method = RequestMethod.GET)
    public String searchBoard(@RequestParam("searchType") String searchType,
                              @RequestParam("searchKeyword") String searchKeyword,
                              @RequestParam(value = "startDate", required = false) String startDate,
                              @RequestParam(value = "endDate", required = false) String endDate,
                              Model model) {
        List<Board> list = boardService.searchBoard(searchType, searchKeyword, startDate, endDate);
        model.addAttribute("boardList", list);
        return "board";
    }
}
