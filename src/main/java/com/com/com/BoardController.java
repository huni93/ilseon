package com.com.com;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
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
	
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);

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
			System.out.println("Files::::: " + files);

		} else {
			model.addAttribute("board", new Board());
			model.addAttribute("mode", "create");
		}
		return "write";
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<byte[]> fileDownload(@RequestParam("fileSeq") long fileSeq) throws IOException {
	    FileUp fileUp = boardService.getFileBySeq(fileSeq);

	    if (fileUp == null || fileUp.getSavePath() == null || fileUp.getSavePath().isEmpty()) {
	        log.warn("Invalid file request: fileSeq = " + fileSeq);
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.TEXT_PLAIN);
	        return new ResponseEntity<byte[]>("Invalid parameters or file does not exist".getBytes(), headers, HttpStatus.BAD_REQUEST);
	    }

	    log.info("Downloading file: fileSeq = " + fileSeq + ", saveName = " + fileUp.getSaveName());
	    
	    // Use savePath directly without adding saveName to it
	    String filePath = fileUp.getSavePath();

	    File file = new File(filePath);
	    if (!file.exists()) {
	        log.error("File not found: " + filePath);
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.TEXT_PLAIN);
	        return new ResponseEntity<byte[]>("File not found".getBytes(), headers, HttpStatus.NOT_FOUND);
	    }

	    byte[] fileContent = Files.readAllBytes(file.toPath());
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    String encodedFileName = URLEncoder.encode(fileUp.getRealName(), "UTF-8").replace("+", "%20");
	    headers.setContentDispositionFormData("attachment", encodedFileName);

	    return new ResponseEntity<byte[]>(fileContent, headers, HttpStatus.OK);
	}
	
	 @RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
	    public ResponseEntity<byte[]> downloadExcel() throws IOException {
	        List<Board> boardList = boardService.list(new Board());

	        // Null check for boardList
	        if (boardList == null) {
	            log.error("boardList is null");
	            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }

	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("게시판 목록");

	        Row headerRow = sheet.createRow(0);
	        String[] headers = {"글번호", "작성자", "제목", "작성일", "수정일", "조회수"};
	        for (int i = 0; i < headers.length; i++) {
	            headerRow.createCell(i).setCellValue(headers[i]);
	        }

	        int rowNum = 1;
	        for (Board board : boardList) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(board.getSeq());
	            row.createCell(1).setCellValue(board.getMem_name());
	            row.createCell(2).setCellValue(board.getBoard_subject());
	            if (board.getReg_date() != null) {
	                row.createCell(3).setCellValue(board.getReg_date().toString());
	            }
	            if (board.getUpt_date() != null) {
	                row.createCell(4).setCellValue(board.getUpt_date().toString());
	            }
	            row.createCell(5).setCellValue(board.getView_cnt());
	        }

	        for (int i = 0; i < headers.length; i++) {
	            sheet.autoSizeColumn(i);
	        }

	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        workbook.write(byteArrayOutputStream);
	        workbook.close();

	        byte[] excelBytes = byteArrayOutputStream.toByteArray();

	        HttpHeaders headers1 = new HttpHeaders();
	        headers1.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers1.setContentDispositionFormData("attachment", "board_list.xlsx");

	        return new ResponseEntity<byte[]>(excelBytes, headers1, HttpStatus.OK);
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
