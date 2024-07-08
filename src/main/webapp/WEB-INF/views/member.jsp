<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<html>
<head>
 <script>
        // 정규 표현식: 영문자 또는 숫자만 허용
        const idPattern = /^[a-zA-Z0-9]+$/;

        // 중복 체크를 위한 예제 데이터베이스 (서버로부터 받아오는 데이터로 교체 가능)
        const existingIds = ["test"];

        // ID 유효성 검사
        function validateId() {
            const id = document.getElementById("id").value;
            if (!idPattern.test(id)) {
                alert("ID는 영문자 또는 숫자만 가능합니다.");
                return false;
            }
            return true;
        }

        // ID 중복 체크
        function checkIdDuplication() {
            const id = document.getElementById("id").value;
            if (!validateId()) return;
            if (existingIds.includes(id)) {
                alert("이미 존재하는 ID입니다.");
            } else {
                alert("사용 가능한 ID입니다.");
            }
        }

        // 비밀번호 확인 검사
        function validateForm() {
            const password = document.getElementById("pass").value;
            const confirmPassword = document.getElementById("pass2").value;
            if (password !== confirmPassword) {
                alert("비밀번호가 일치하지 않습니다.");
                return false;
            }

            // 이메일 유효성 검사 추가
            const email1 = document.getElementById("email1").value;
            const email2 = document.getElementById("email2").value;
            const email = email1 + "@" + email2;
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailPattern.test(email)) {
                alert("유효한 이메일 주소를 입력하세요.");
                return false;
            }

            // 주민등록번호 유효성 검사
            const jumin1 = document.getElementById("jumin1").value;
            const jumin2 = document.getElementById("jumin2").value;
            if (!validateJumin(jumin1, jumin2)) {
                return false;
            }

            return true;
        }

        // 이름 입력 시 실시간 유효성 검사
        function fncNameKeyUp(element) {
            const name = element.value;
            const namePattern = /^[a-zA-Z가-힣]+$/;
            if (name.length > 5) {
                alert("이름은 5글자 이하로 입력해주세요.");
                element.value = name.substring(0, 5);  // 5글자까지만 남기고 잘라냄
            }
            if (!namePattern.test(name)) {
                alert("이름은 한글 또는 영문자만 가능합니다.");
                element.value = name.replace(/[^a-zA-Z가-힣]/g, '');  // 허용되지 않는 문자 제거
            }
        }

        // 휴대폰 번호 입력 시 숫자만 허용하고 4자리 입력 시 다음 필드로 이동
        function handlePhoneInput(event, nextElementId) {
            const input = event.target;
            input.value = input.value.replace(/[^0-9]/g, ''); // 숫자만 허용
            if (input.value.length >= 4) {
                document.getElementById(nextElementId).focus(); // 다음 필드로 이동
            }
        }

     // 주민번호 유효성 검사 및 가리기
        function validateJumin() {
            const jumin1 = document.getElementById("jumin1").value;
            const jumin2 = document.getElementById("jumin2").value;

            // 주민등록번호는 숫자로만 입력되어야 함
            const numberPattern = /^[0-9]*$/;
            if (!numberPattern.test(jumin1) || !numberPattern.test(jumin2)) {
                alert("주민등록번호는 숫자로만 입력 가능합니다.");
                return false;
            }

            // 주민등록번호 앞자리와 뒷자리 길이 확인
            if (jumin1.length !== 6 || jumin2.length !== 7) {
                alert("유효한 주민등록번호를 입력하세요.");
                return false;
            }

            // 주민등록번호 뒷자리 첫 번째 숫자는 숫자여야 함
            if (jumin2.charAt(0) < '1' || jumin2.charAt(0) > '4') {
                alert("주민등록번호 뒷자리 첫 번째 숫자는 1에서 4 사이의 숫자여야 합니다.");
                return false;
            }

            return true;
        }

         // 실시간 주민번호 뒷자리 가리기
            function maskJumin2() {
                const jumin2Field = document.getElementById("jumin2");
                const jumin2 = jumin2Field.value;

                if (jumin2.length > 1) {
                    jumin2Field.value = jumin2.charAt(0) + jumin2.substring(1).replace(/\d/g, '*');
                }
            }
    </script>
</head>
<body>
    <form onsubmit="return validateForm()">
        <table width="1400" height="650">
            <tr>
                <td width="100%" height="10%">회원가입</td>
            </tr>
            <tr>
                <td height="60%" align="center" valign="top">
                    <hr>
                    <br>
                    <p align="left" style="padding-left: 160px">
                        <br>
                        <br> ID : <input type="text" size="10" maxlength="15" name="id" id="id"> 
                        <input type="button" name="idChk" value="중복체크" onclick="checkIdDuplication()"> <br>
                        <br> 비밀번호 : <input type="password" size="15" maxlength="20"
                            name="pass" id="pass"><br>
                        <br> 비밀번호 확인 : <input type="password" size="15"
                            maxlength="20" name="pass2" id="pass2"><br>
                        <br> 이름 : <input type="text" size="13" name="name" id="name"
                            onkeyup="fncNameKeyUp(this)"><br>
                        <br> 이메일 : <input type="text" size="15" name="email1"
                            id="email1">@<input type="text" size="15" name="email2"
                            id="email2"><br>
                        <br> 휴대폰 : <select name="ph1">
                            <option value="010">010</option>
                            <option value="011">011</option>
                            <option value="016">016</option>
                            <option value="017">017</option>
                            <option value="019">019</option>
                        </select> - <input type="text" name="ph2" size="5" maxlength="4" id="ph2" oninput="handlePhoneInput(event, 'ph3')">
                        - <input type="text" name="ph3" size="5" maxlength="4" id="ph3"><br>
                        <br> 성별 : <input type="radio" name="gender" value="남자">
                        남자&nbsp;&nbsp; <input type="radio" name="gender" value="여자">
                        여자<br>
                         <br> 주민번호 : 
                        <input type="text" name="jumin1" id="jumin1" maxlength="6" oninput="if(this.value.length === 6) document.getElementById('jumin2').focus()"> - 
                        <input type="text" name="jumin2" id="jumin2" maxlength="7" oninput="maskJumin2()"><br>
                        <br> 주소 : <input type="text" name="address" size="15"
                            maxlength="15"><br> *주소는 (시/도)만 입력해주세요 (예: 경기도,
                        서울특별시, 경상남도 등)
                    </p>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <hr>
                    <br> <input type="submit" id="regi_btn" value="가입신청">&nbsp;
                    <input type="reset" value="다시입력">&nbsp; <input
                    type="button" value="취소">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
