<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>게시판</title>
<script>
   document.addEventListener("DOMContentLoaded", function(){
       document.getElementById("btnDelete").addEventListener("click", function(){
           var form = document.getElementById("deleteForm");
           var selectedIds = document.querySelectorAll('input[name="selectedIds"]:checked');
           var seqList = [];
           selectedIds.forEach(function(checkbox){
               seqList.push(checkbox.value);         
           });
           if(seqList.length === 0){
               alert("선택된 항목이 없습니다.");
               return;
           }
           var seqListInput = document.createElement("input");
           seqListInput.setAttribute("type", "hidden");
           seqListInput.setAttribute("name", "seqList");
           seqListInput.setAttribute("value", seqList.join(","));
           form.appendChild(seqListInput);
           form.submit();
       });
   });

   function toggleCheckboxes() {
       var selectAll = document.getElementById("selectAll");
       var checkboxes = document.querySelectorAll('input[name="selectedIds"]');
       checkboxes.forEach(function(checkbox) {
           checkbox.checked = selectAll.checked;
       });
   }
</script>
<style>
   table { border-collapse: collapse; }
   th, td {
      border: 1px solid black;
   }
</style>
</head>
<body>

<!-- 검색 폼 추가 -->
<form id="searchForm" action="/searchBoard" method="get">
    <select name="searchType">
        <option value="mem_name">작성자</option>
        <option value="subject">제목</option>
        <option value="subject_content">제목 + 내용</option>
    </select>
    <input type="text" name="searchKeyword" placeholder="검색어를 입력하세요">
    <div>
    <input type="date" name="startDate" placeholder="시작 날짜">
    ~
    <input type="date" name="endDate" placeholder="끝 날짜">
    <button type="submit">검색</button>
    </div>
</form>

<form id="deleteForm" action="/deleteBoard" method="post">
 
   <div>
      <a href="/write">글쓰기</a>
      <button type="button" id="btnDelete">삭제</button>
   </div>
     <table>
   <thead>
   <tr>
      <th><input type="checkbox" id="selectAll" onclick="toggleCheckboxes()"></th>
      <th>글번호</th>
      <th>작성자</th>
      <th>제목</th>
      <th>작성일</th>
      <th>수정일</th>
      <th>조회수</th>
   </tr>
   </thead>
    <tbody>
        <!-- 게시글 목록 출력 -->
        <c:forEach var="board" items="${boardList}">
            <tr>
                <td><input type="checkbox" name="selectedIds" value="${board.seq}"></td>
                <td>${board.seq}</td>
                <td>${board.mem_name}</td>
                <td><a href="/write?seq=${board.seq}">${board.board_subject}</a></td>
                <td><fmt:formatDate value="${board.reg_date}" pattern="yyyy-MM-dd" /></td>
                <td><fmt:formatDate value="${board.upt_date}" pattern="yyyy-MM-dd" /></td>
                <td>${board.view_cnt}</td>
            </tr>
        </c:forEach>
    </tbody>
   </table>
</form>
</body>
</html>
