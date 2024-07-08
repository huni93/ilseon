<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>대리결재 폼</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
            color: #333;
        }
        .container {
            background-color: #ffffff;
            border: 1px solid #ccc;
            border-radius: 8px;
            padding: 20px;
            width: 300px; /* Adjust as needed */
            margin: 0 auto;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="text"], select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .left-aligned {
            text-align: left; /* 왼쪽 정렬 */
        }
        .buttons {
            display: flex;
            justify-content: space-between; /* This will space out the buttons */
        }
        button {
            padding: 10px;
            border: none;
            background-color: #007bff;
            color: white;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            flex: 1; /* This makes each button take up equal space */
            margin: 2px; /* Slightly space out the buttons */
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
   <script>
function updateRank() {
    var select = document.getElementById("proxySelect");
    var selectedOption = select.options[select.selectedIndex];
    var rankCode = selectedOption.getAttribute("data-rank");
    var rankName = convertRank(rankCode);  // 직급 코드를 직급명으로 변환

    var rankInput = document.getElementById("rank");
    if (rankInput) {
        rankInput.value = rankName;  // 선택된 대리결재자의 직급명으로 직급 필드 업데이트
    } else {
        console.error("Rank input not found");
    }
}

function convertRank(rankCode) {
    switch (rankCode) {
        case "001": return "사원";
        case "002": return "대리";
        case "003": return "과장";
        case "004": return "부장";
        default: return "알 수 없음";
    }
}
</script>
</head>
<body>
    <form action="/updateProxy" method="post" onsubmit="submitAndClose()">
    <div class="container">
        <h2>대리 결재</h2>
        <div class="form-group">
            <label for="category">대리결재자:</label>
            <select id="proxySelect" name="proxyId" onchange="updateRank()">
		    <c:forEach items="${selectproxy}" var="proxy">
		        <!-- 멤버 테이블에서 가져온 대리결재자의 직급 정보를 data-rank 속성으로 추가 -->
		        <option value="${proxy.proxyId}" data-rank="${proxy.memberRank}">${proxy.proxyName}</option>
		    </c:forEach>
		</select>
        </div>
        <div class="form-group left-aligned">
		    <label for="rank">직급:</label>
		    <input type="text" id="rank" readonly>
		</div>
        <div class="form-group">
            <label for="details">대리자:</label>
            <c:set var="rankLabel">
                <c:choose>
                    <c:when test="${memberRank == '003'}">과장</c:when>
                    <c:when test="${memberRank == '004'}">부장</c:when>
                    <c:otherwise>알 수 없음</c:otherwise>
                </c:choose>
            </c:set>
            <input type="text" id="details" value="${memberName} (${rankLabel})" placeholder="대리자 이름(직급)" readonly>
        </div>
        <div class="buttons">
            <button type="button" onclick="window.close()">취소</button>
            <button type="submit">승인</button>
        </div>
    </div>
    </form>
    
    <script>
        function submitAndClose() {
            window.onunload = closeWindow;
            return true; // Ensure the form still submits
        }

        function closeWindow() {
            window.close();
        }
    </script>
</body>
</html>