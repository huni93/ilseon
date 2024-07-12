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

   #fileInputsContainer {
        display: flex;
        flex-direction: column; /* 수직 방향으로 파일 입력 필드 정렬 */
    }

    #fileInputsContainer input {
        margin-top: 5px; /* 입력 필드 간 간격 조정 */
    }
</style>
<script type="text/javascript">
	function goToList() {
		window.location.href = 'board';
	}
	
	function addFileInputIfNeeded(input) {
        if (input.files.length > 0) {
            var reader = new FileReader();

            reader.onload = function(e) {
                var img = new Image();
                img.onload = function() {
                    // 이미지 크기 검증
                    if (this.width > 500 || this.height > 500) {
                        alert("이미지의 크기는 가로 및 세로 500픽셀을 초과할 수 없습니다.");
                        input.value = "";  // 파일 선택 취소
                    } else {
                        addNewInputField(input);
                    }
                };
                img.src = e.target.result;
            };
            reader.readAsDataURL(input.files[0]);
        }
    }

    function addNewInputField(input) {
        if (!input.nextElementSibling) {  // 취소 버튼 추가
            addCancelButton(input);
        }

        var container = document.getElementById("fileInputsContainer");
        var existingInputs = container.querySelectorAll("input[type='file']");
        
        // 이미지 검증 통과 후에만 새로운 입력 필드 추가 (최대 5개 제한)
        if (existingInputs.length < 5) {
            var newInputWrapper = document.createElement("div");
            newInputWrapper.className = "file-input-wrapper";
            var newInput = document.createElement("input");
            newInput.type = "file";
            newInput.name = "files";
            newInput.accept = "image/*";
            newInput.setAttribute("onchange", "addFileInputIfNeeded(this)");
            newInputWrapper.appendChild(newInput);
            container.appendChild(newInputWrapper);
        }
    }

    function addCancelButton(input) {
        var cancelButton = document.createElement("button");
        cancelButton.textContent = "취소";
        cancelButton.type = "button";
        cancelButton.onclick = function() {
            var wrapper = input.parentElement;
            wrapper.parentElement.removeChild(wrapper);
        };
        input.parentElement.appendChild(cancelButton);
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
	<form action="${pageContext.request.contextPath}/write" method="post" enctype="multipart/form-data">
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
		
		<!-- 파일 업로드 필드 -->
    <div id="fileInputsContainer">
        <div class="file-input-wrapper">   
        <input type="file" name="files" accept="image/*" onchange="addFileInputIfNeeded(this); validateImageSize(this);">
    </div>
    </div>
    
    <div>
    <h3>첨부파일</h3>
    <ul>
    <c:forEach var="file" items="${files}">
        <li>
        <a href="${pageContext.request.contextPath}/download?fileSeq=${file.fileSeq}&saveName=${URLEncoder.encode(file.saveName, 'UTF-8')}">
            ${file.realName}
        </a>
    </li>
    </c:forEach>
   </ul>
</div>


	
</form>
</body>
</html>
