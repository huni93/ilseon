<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<script>
	$("selector").api();

	$("txt1").val();

	//selector 
	$("input#txt1").val();
	$("input .txt111").val(); //한칸 띄우는게 하위태크 
	$("[type-text]").val();
	$("div")
	$("table > tr > td") //이중 맨위에있는 td 를 가르킨다
	
	$("table tr:eq(0) td").eq(2).val();  //table은 빼도됌

	//#id, .class , [name='txt1']

	document.getElementById('txt1').value;
	document.querySelector("");

	// type 을 잡을수밖에 없을때

	$("[type=text]").val(); // 이렇게하면 제일 위에있는걸 가져온다 
	$("[type=text]").eq(2).val(); //index 값 제일 마지막에 있는 걸 가져옴
	$("[type=txt2]").length; // 1 가르킨 input이 몇개냐
	$("[type=txt2]").val().length; // 4
	
	
	//꼭 써야함 이벤트함수 
	$(function() {
		$("#btn").click(function(){
			var txt = $("table tr td").eq(2).text();   // 나옴
			var val = $("table tr td").eq(2).val();   // 안나옴
			var txt = $("table tr td").children().children().children().eq(2).text(); //td로 넘아감  tbody를 고려해서 3번써야함.  
			//children 을 쓰면  테이블에 tbody가 들어감 
		})
	})
</script>
</head>
<body>
<div>
   <input type = "text" name = "txt" id ="txt" class ="" > 
</div>

	<input type="text" name="txt1" id="text1" class="text" value="1111">
	<input type="text" name="txt2" id="text2" value="abcd">
	<input type="text" name="txt3" id="text3" value="1234">

//네임을 못쓰면 밸류도못씀 
//select 는 밸류와 속성을 둘다사용가능
//브라우저에서 사용자가 수정이가능하다면 그건 밸류  수정이불가능하다면 네임(속성)
//백단으로 넘길때 네임이 있는애들만 넘길수있음 

<select>
<textarea rows="" cols="" name =textarea> sfsdfsdffsf </textarea>

<div> adfsdfsfafadf</div> $("div").text(); 

</select>

<from>   // 3개만 백단으로넘어감 네임속성
    <input type="text" name="a1" >
	<input type="text" name="a1" id= "aa" >
	<input type="text" id="a3">
	<input type="text" name="a4" class="a4">
	<input type="text" class="a5">
</from>


	<table> .val()  .text() 차이
		<tr>
			<td value ="1111">11</td> 밸류를 쓸수는 있지만 기능적으로는 사용이불가능하다. td 안에 기능적으로 사용할수있는 속성들은 따로있음.
			<td>22</td>
			<td>33</td>  
			<td>44</td>
		</tr>
		
		
	</table>
</body>
</html>