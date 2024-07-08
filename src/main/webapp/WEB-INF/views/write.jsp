<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성 폼</title>
<style>
.button-wrapper {
	display: inline-block;
	margin-right: 10px;
}

.buttons {
	margin-top: 10px;
}
</style>
<script type="text/javascript">
	function goToList() {
		window.location.href = 'board';
	}
</script>
</head>
<body>

	<h2>
		<c:choose>
			<c:when test="${mode == 'update'}">게시글 수정 폼</c:when>
			<c:otherwise>게시글 작성 폼</c:otherwise>
		</c:choose>
	</h2>
	<form action="${pageContext.request.contextPath}/write" method="post">
		<input type="hidden" name="seq" value="${board.seq}" /> <input
			type="hidden" name="mode" value="${mode}" /> <label for="mem_name">작성자:</label>
		<input type="text" id="mem_name" name="mem_name"
			value="${board.mem_name}" required><br> <label
			for="mem_id">ID:</label> <input type="text" id="mem_id" name="mem_id"
			value="${board.mem_id}" required><br> <label
			for="board_subject">제목:</label> <input type="text" id="board_subject"
			name="board_subject" value="${board.board_subject}" required><br>

		<label for="board_content">내용:</label><br>
		<textarea id="board_content" name="board_content" rows="5" required>${board.board_content}</textarea>
		<br> <br>

		<div class="buttons">
			<div class="button-wrapper">
				<c:choose>
					<c:when test="${mode == 'update'}">
						<input type="submit" value="수정">
					</c:when>
					<c:otherwise>
						<input type="submit" value="등록">
					</c:otherwise>
				</c:choose>
			</div>
			<div class="button-wrapper">
				<input type="button" value="목록" onclick="goToList()">
			</div>
		</div>
	</form>

</body>
</html>
