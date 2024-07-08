<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<script>
	function submitForm() {
		document.getElementById('updateForm').submit();
	}
	
	
	//seq 새글일때 안넘길꺼임
	document.getElementById('updateForm').addEventListener('submit', function() {
	    if (!document.querySelector('input[name="seq"]')) {
	        alert('새 글 작성 중입니다.');
	    } else {
	        alert('기존 글 수정 중입니다.');
	    }
	});

</script>
</head>
<body>
	<h2>
		<c:choose>
			<c:when test="${mode == 'update'}">게시글 상세보기</c:when>
			<c:otherwise>게시글 작성 폼</c:otherwise>
		</c:choose>
	</h2>
	<form id="updateForm"
		action="${pageContext.request.contextPath}/saveBoard" method="post">
		<table>
			<tr>
				<th>체크박스</th>
                <td>
                    <!-- 결재요청, 과장, 부장 체크박스 -->
                    <input type="checkbox" name="approvalType" value="결재대기" disabled
                        <c:if test="${approvalStatus == '02' || approvalStatus == '03' || approvalStatus == '04'}">checked</c:if>> 결재대기
                    <input type="checkbox" name="approvalType" value="과장" disabled
                        <c:if test="${approvalStatus == '03' || approvalStatus == '04'}">checked</c:if>> 과장
                    <input type="checkbox" name="approvalType" value="부장" disabled
                        <c:if test="${approvalStatus == '04'}">checked</c:if>> 부장
                </td>
			</tr>
			<tr>
				<th>번호</th>
				<td>
						<c:choose>
		            <c:when test="${not empty board.seq}">
		                ${board.seq}
		                <input type="hidden" name="seq" value="${board.seq}">
		            </c:when>
		            <c:otherwise>
		                <!-- 새 글 작성 시 seq 필드를 전송하지 않음 -->
		            </c:otherwise>
        </c:choose>

                </td>
			</tr>
			<tr>
			    <th>작성자</th>
			    <td>
			        <c:choose>
			            <c:when test="${not empty board.memberName}">
			                ${board.memberName}
			                <input type="hidden" name="memberName" value="${board.memberName}">
			            </c:when>
			            <c:otherwise>
			                ${sessionScope.member.memberName} <!-- 현재 로그인한 사용자의 이름 -->
			                <input type="hidden" name="memberName" value="${sessionScope.member.memberName}">
			            </c:otherwise>
			        </c:choose>
			    </td>
			</tr>

			<tr>
			    <th>제목</th>
			    <td>
			        <input type="text" name="boardTitle" size="50" value="${board.boardTitle}"
			            ${mode != 'update' || (board.apprstat == '01' || (board.apprstat == '05' && member.memberId == board.memberId)) ? '' : 'readonly'}>
			    </td>
			</tr>
			<tr>
			    <th>내용</th>
			    <td>
			        <textarea name="boardContent" cols="70" rows="10"
			            ${mode != 'update' || (board.apprstat == '01' || (board.apprstat == '05' && member.memberId == board.memberId)) ? '' : 'readonly'}>${board.boardContent}</textarea>
			    </td>
			</tr>


		</table>

		<div style="text-align: center;">
    <c:choose>
        
        <c:when test="${mode == 'create'}">
            <input type="submit" value="임시저장" name="submit_type">
            <input type="submit" value="결재" name="submit_type">
        </c:when>
               
        <c:when test="${mode == 'update'}">
            <c:choose>
            
                <c:when test="${board.apprstat == '05' && member.memberId == board.memberId}">
                    <input type="submit" value="임시저장" name="submit_type">
                    <input type="submit" value="결재" name="submit_type">
                </c:when>
            
              <c:when test="${board.apprstat == '01'}">
                    <input type="submit" value="임시저장" name="submit_type">
                    <input type="submit" value="결재" name="submit_type">
                </c:when>
            
                <c:when test="${memberRank == '001' or memberRank == '002'}">
                    <c:if test="${!isAuthor}">
                        <c:if test="${!isApprovalPending && !isRejected && board.apprstat != '03'}">
                            <input type="submit" value="임시저장" name="submit_type">
                            <input type="submit" value="결재" name="submit_type">
                        </c:if>                      
                    </c:if>                  
                </c:when>
                <c:when test="${memberRank == '003'}">
                    <c:if test="${isApprovalPending && !isRejected && board.apprstat != '03'}">
                        <input type="submit" value="반려" name="submit_type">
                        <input type="submit" value="결재" name="submit_type">
                    </c:if>
                </c:when>
                <c:when test="${memberRank == '004'}">
                    <c:if test="${board.apprstat == '03'}">
                        <input type="submit" value="결재" name="submit_type">
                        <input type="submit" value="반려" name="submit_type">
                    </c:if>
                </c:when>
            </c:choose>
        </c:when>
    </c:choose>
</div>		
	</form>

	<c:choose>
		<c:when test="${mode == 'update'}">
			<!-- 게시글 상세보기일 때만 히스토리 테이블 표시 -->
			<h2>히스토리</h2>
			<table border="1">
				<thead>
					<tr>
						<th>번호</th>
						<th>결재일</th>
						<th>결재자</th>
						<th>결재상태</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="history" items="${historyList}">
						<tr>
							<td>${history.hiNum}</td>
							<td><fmt:formatDate value="${history.signDate}"
									pattern="yy-dd-MM" /></td>
							<td>${history.memberName}</td>
							<td><c:choose>
									<c:when test="${history.signStatus == '01'}">임시저장</c:when>
									<c:when test="${history.signStatus == '02'}">결재대기</c:when>
									<c:when test="${history.signStatus == '03'}">결재중</c:when>
									<c:when test="${history.signStatus == '04'}">결재완료</c:when>
									<c:when test="${history.signStatus == '05'}">반려</c:when>

								</c:choose></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<!-- 게시글 작성 폼일 때는 아무 내용도 표시하지 않음 -->
		</c:otherwise>
	</c:choose>
</body>
</html>
