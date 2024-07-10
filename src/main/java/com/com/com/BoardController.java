package com.com.com;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.com.com.dto.Board;
import com.com.com.dto.FileUp;
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
			List<FileUp> files = boardService.getFilesByBoardSeq(seq);
			model.addAttribute("board", board);
			model.addAttribute("files", files); // 파일 목록 추가   
			model.addAttribute("mode", "update");	

		} else {
			model.addAttribute("board", new Board());
			model.addAttribute("mode", "create");
		}
		return "write";
	}


	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute("board") Board board, 
	                    @RequestParam("mode") String mode, 
	                    @RequestParam("files") MultipartFile[] files, // 파일 파라미터 추가
	                    Model model) {
	    if ("update".equals(mode)) {
	        boardService.updateBoard(board); // 게시글 업데이트	        
	        model.addAttribute("message", "게시글이 성공적으로 수정되었습니다.");
	    } else {
	        boardService.insertBoard(board); // 게시글 삽입
	        model.addAttribute("message", "게시글이 성공적으로 등록되었습니다.");
	    }

	    // 파일 처리 로직
        if (files != null) {
            for (MultipartFile uploadedFile : files) {
                if (!uploadedFile.isEmpty()) {
                    try {
                        String saveName = UUID.randomUUID().toString(); // 파일 이름 생성
                        String originalFilename = uploadedFile.getOriginalFilename();
                        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                        String savePath = "C:\\Users\\dev\\Desktop\\pkh\\FileUp"; // 저장 경로 설정

                        File directory = new File(savePath);
                        if (!directory.exists()) directory.mkdirs(); // 디렉토리가 없다면 생성

                        File destinationFile = new File(directory, saveName + extension);
                        uploadedFile.transferTo(destinationFile); // 파일 저장

                        FileUp file = new FileUp();
                        file.setRealName(originalFilename);
                        file.setSaveName(saveName + extension);
                        file.setRegDate(new Date());
                        file.setSavePath(destinationFile.getAbsolutePath());
                        file.setListSeq(board.getSeq());
                        boardService.saveFile(file); // 데이터베이스에 파일 정보 저장
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "Error  " + e.getMessage();
                    }
                }
            }
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
