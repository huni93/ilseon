<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
    <style>
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .buttons {
            margin-top: 10px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
    var originalTableContent = $('.list-table tbody').html(); // 원본 테이블 내용 저장

    // 결재상태 옵션 가져오기
    $.ajax({
        url: '/getApprovalStatuses',
        method: 'GET',
        success: function(data) {
            var selectElement = $('select[name="approvalStatus"]');
            selectElement.empty();
            selectElement.append('<option value="">결재상태</option>');
            $.each(data, function(index, status) {
                selectElement.append('<option value="' + status.value + '">' + status.label + '</option>');
            });         
        },
        error: function(xhr, status, error) {
            console.error('결재 상태 가져오기 실패:', error);
        }
    });

    // 결재상태 선택이 변경될 때 이벤트 핸들러
    $('select[name="approvalStatus"]').change(function() {
        var selectedStatus = $(this).val();
        filterBoardsByApprovalStatus(selectedStatus, originalTableContent);
    });
});

function filterBoardsByApprovalStatus(status, originalContent) {
    $('.list-table tbody').html(originalContent); // 원본 내용으로 테이블 초기화
    var allBoards = $('.list-table tbody tr');
    var visibleBoards = 0; // 카운터로 사용될 변수

    if (status === "" || status === "all") {
        allBoards.show();
        visibleBoards = allBoards.length;
    } else {
        allBoards.hide();
        allBoards.each(function() {
            var boardStatusText = $(this).find('td').eq(6).text().trim();
            if (boardStatusText === getStatusLabel(status)) {
                $(this).show();
                visibleBoards++;
            }
        });
    }

    // 모든 행을 숨긴 후, 맞는 상태의 게시글이 하나도 없으면 메시지 표시
    if (visibleBoards === 0) {
        $(".list-table tbody").html('<tr><td colspan="7">해당 상태의 게시글이 없습니다.</td></tr>');
    }
}

function getStatusLabel(status) {
    switch (status) {
        case '01': return '임시저장';
        case '02': return '결재대기';
        case '03': return '결재중';
        case '04': return '결재완료';
        case '05': return '반려';
        default: return '';
    }
}
//게시글 아무데나 터치시 넘어가게 하지만 게시글이 없는 메시지는 제외
$(document).ready(function() {
    $(".list-table tbody tr").click(function() {
        // 데이터가 있는 행만 선택
        var seq = $(this).data("seq");
        if (seq) { // seq가 정의된 경우에만 페이지 이동
            window.location.href = '/awrite?seq=' + seq;
        }
    });
    
    // 모든 행에 대해 커서를 포인터로 설정, 데이터가 없는 행은 디폴트 커서로 설정
    $(".list-table tbody tr").css("cursor", function() {
        return $(this).data("seq") ? "pointer" : "default";
    });
});
//대리결제 팝업창
function openProxyWindow() {
    var width = 600;
    var height = 500;
    var left = (window.screen.width / 2) - (width / 2);
    var top = (window.screen.height / 2) - (height / 2);

    // window.open(URL, name, specs, replace)
    var proxyWindow = window.open("/proxyForm", "Proxy", "width=" + width + ",height=" + height + ",top=" + top + ",left=" + left + ",resizable=yes,scrollbars=yes");

    if (proxyWindow) {
        // 포커스를 새 창에 맞춤
        proxyWindow.focus();
    } else {
        alert('팝업 차단을 해제해주세요.');
    }
}

// 직급을 매핑하는 객체
const rankMappings = {
    "001": "사원",
    "002": "대리",
    "003": "과장",
    "004": "부장"
};

// 페이지가 로드되면 실행되는 함수
window.onload = function() {
    // 모든 memberRank 요소를 찾습니다.
    const rankElements = document.querySelectorAll('.member-rank');

    // 각 요소에 대해
    rankElements.forEach(function(el) {
        // 해당 직급 코드를 통해 직급명을 찾아 표시합니다.
        el.textContent = rankMappings[el.textContent.trim()] || "알 수 없는 직급";
    });
};
</script>


</head>
<body>
     <div class="header">
    <div class="user-info">
        <c:choose>
        <c:when test="${not empty sessionScope.proxyInfo}">
            <!-- 대리 결재자 정보가 있을 때 -->
            <p>
                ${sessionScope.member.memberName} (<span class="member-rank">${member.memberRank}</span>) 
                 -
                ${sessionScope.proxyInfo.apperName} (<span class="member-rank">${member.memberRank}</span>) 
                                          님 환영합니다.
            </p>
            대리 결재일: <fmt:formatDate value="${sessionScope.proxyInfo.proxyDate}" pattern="MM-dd" /> <br>
        </c:when>
        <c:otherwise>
            <!-- 대리 결재자 정보가 없을 때 -->
            <p>
                ${sessionScope.member.memberName} (<span class="member-rank">${member.memberRank}</span>) 
                님 환영합니다.
            </p>
        </c:otherwise>
    </c:choose>
        <a href="/logout">로그아웃</a>
    </div>
</div>

    <div class="buttons">
        <button onclick="location.href='/awrite'">글쓰기</button>
     <c:if test="${sessionScope.member.memberRank eq '003' || sessionScope.member.memberRank == '004'}">
        <button onclick="openProxyWindow()">대리결재</button>
    </c:if>
</div>

    <form id="AsearchForm" action="/AsearchBoard" method="get">
    <select name="searchType">
        <option value="">선택</option>
        <option value="memberName" ${param.searchType == 'memberName' ? 'selected' : ''}>작성자</option>
        <option value="boardTitle" ${param.searchType == 'boardTitle' ? 'selected' : ''}>제목</option>
        <option value="boardContent" ${param.searchType == 'boardContent' ? 'selected' : ''}>제목 + 내용</option>
    </select>
    <input type="text" name="searchKeyword" placeholder="검색어를 입력하세요" value="${param.searchKeyword}">
    
    <select name="approvalStatus">
        <option value="">결재상태</option>
        <!-- Options will be dynamically loaded here -->
    </select>
    
    <div>
        <input type="date" name="startDate" placeholder="시작 날짜" value="${param.startDate}"> ~ 
        <input type="date" name="endDate" placeholder="끝 날짜" value="${param.endDate}">
        <button type="submit">검색</button>
    </div>
</form>


    <table class="list-table">
        <thead>
            <tr>
                <th>번호</th>
                <th>작성자</th>
                <th>제목</th>
                <th>작성일</th>
                <th>결재일</th>
                <th>결재자</th>
                <th>결재상태</th>
            </tr>
        </thead>
         <tbody>
            <c:choose>
                <c:when test="${empty boardList and empty approvalPendingBoards}">
                    <tr><td colspan="8">게시글이 없습니다.</td></tr>
                </c:when>
                <c:otherwise>
                    <c:if test="${not empty boardList}">
                        <c:forEach var="board" items="${boardList}">
                            <tr data-seq="${board.seq}">
                                <td>${board.seq}</td>
                                <td>${board.memberName}</td>
                                <td>${board.boardTitle}</td>
                                <td><fmt:formatDate value="${board.regDate}" pattern="yy-MM-dd" /></td>
                                <td><fmt:formatDate value="${board.apprDate}" pattern="yy-MM-dd" /></td>
                                <td>${board.approver}</td>
                                <td>
                        <c:choose>
                            <c:when test="${board.apprstat == '01'}">임시저장</c:when>
                            <c:when test="${board.apprstat == '02'}">결재대기</c:when>
                            <c:when test="${board.apprstat == '03'}">결재중</c:when>
                            <c:when test="${board.apprstat == '04'}">결재완료</c:when>
                            <c:when test="${board.apprstat == '05'}">반려</c:when>
                        </c:choose>
                    </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${not empty approvalPendingBoards}">
                        <c:forEach var="board" items="${approvalPendingBoards}">
                            <tr data-seq="${board.seq}">
                                <td>${board.seq}</td>
                                <td>${board.memberName}</td>
                                <td>${board.boardTitle}</td>
                                <td><fmt:formatDate value="${board.regDate}" pattern="yy-MM-dd" /></td>
                                <td><fmt:formatDate value="${board.apprDate}" pattern="yy-MM-dd" /></td>
                                <td>${board.approver}</td>
                                <td>
                        <c:choose>
                            <c:when test="${board.apprstat == '01'}">임시저장</c:when>
                            <c:when test="${board.apprstat == '02'}">결재대기</c:when>
                            <c:when test="${board.apprstat == '03'}">결재중</c:when>
                            <c:when test="${board.apprstat == '04'}">결재완료</c:when>
                            <c:when test="${board.apprstat == '05'}">반려</c:when>
                        </c:choose>
                    </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>
