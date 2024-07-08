<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>체크박스 예제</title>
<style>
    #checkboxContainer {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
    }

    #checkboxContainer input[type="checkbox"] {
        margin-right: 5px; /* 체크박스 오른쪽 여백 조정 */
    }

    #checkboxContainer label {
        margin-left: 0;
        margin-right: 10px; /* 라벨 왼쪽 여백 없애기 */
    }
</style>
</head>
<body>
<input type="checkbox" id="selectAll"> 전체
<input type="button" id="checkButton" value="버튼">
<div id="checkboxContainer">
    <input type="checkbox" id="checkbox1"> <label for="checkbox1">서울</label>
    <input type="checkbox" id="checkbox2"> <label for="checkbox2">인천</label>
    <input type="checkbox" id="checkbox3"> <label for="checkbox3">경기</label>
    <input type="checkbox" id="checkbox4"> <label for="checkbox4">강원</label>
    <input type="checkbox" id="checkbox5"> <label for="checkbox5">부산</label>
    <input type="checkbox" id="checkbox6"> <label for="checkbox6">대전</label>
    <input type="checkbox" id="checkbox7"> <label for="checkbox7">전남</label>
    <input type="checkbox" id="checkbox8"> <label for="checkbox8">제주</label>
    <input type="checkbox" id="checkbox9"> <label for="checkbox9">평양</label>
</div>
<div id="selectedItems"></div>
<div id="selectedLocations"></div>

<script>
document.addEventListener("DOMContentLoaded", function() {
    const selectAllCheckbox = document.getElementById("selectAll");
    const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#selectAll)');
    const selectedItemsDiv = document.getElementById("selectedItems");   

    // 선택된 체크박스 항목을 저장할 배열 선언
    let selectedCheckboxes = [];

    // 전체 선택 체크박스 이벤트 리스너
    selectAllCheckbox.addEventListener("change", function() {
        checkboxes.forEach(checkbox => {
            checkbox.checked = selectAllCheckbox.checked;
        });
        updateSelectedItems();
        updateSelectedLocations();
    });

    // 각 체크박스 이벤트 리스너
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener("change", function() {
            if (!this.checked && selectAllCheckbox.checked) {
                selectAllCheckbox.checked = false;
            } else if ([...checkboxes].every(checkbox => checkbox.checked)) {
                selectAllCheckbox.checked = true;
            }
            updateSelectedCheckboxes(this); // 선택된 체크박스 배열 업데이트
            updateSelectedItems();
            updateSelectedLocations();
        });
    });

    // 선택된 체크박스 배열 업데이트 함수
    function updateSelectedCheckboxes(checkbox) {
        if (checkbox.checked) {
            // 체크된 항목을 배열 끝에 추가
            selectedCheckboxes.push(checkbox);
        } else {
            // 체크 해제된 항목을 배열에서 제거
            selectedCheckboxes = selectedCheckboxes.filter(item => item !== checkbox);
        }
    }

    // 선택된 항목 업데이트
    function updateSelectedItems() {
        const selectedItems = selectedCheckboxes
            .filter(checkbox => checkbox.labels[0]) // labels[0]가 존재하는지 확인
            .map(checkbox => checkbox.labels[0].textContent)
            .join(", ");
        selectedItemsDiv.textContent = selectedItems; // 새로운 내용을 표시합니다.
    }

    function updateSelectedLocations() {
        selectedLocationsDiv.innerHTML = ""; // 기존 요소 비우기
        selectedCheckboxes.forEach(checkbox => {
            if (checkbox.checked) {
                const locationName = checkbox.labels[0].textContent;
                const locationSpan = document.createElement("span"); // span 요소 생성
                locationSpan.textContent = locationName;
                selectedLocationsDiv.appendChild(locationSpan); // div에 추가
            }
        });
    }

    // 버튼 클릭 이벤트 리스너
    const checkButton = document.getElementById("checkButton");
    checkButton.addEventListener("click", function() {
        const checkedCount = selectedCheckboxes.length;
        if (selectAllCheckbox.checked || checkedCount <= 4) {
            alert("성공");
        } else {
            alert("실패");
        }
    });
});
</script>
</body>
</html>
