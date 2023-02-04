<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.List" %>
<%@ page import = "com.springlab.member.MemberDTO" %>

<!-- 세션에 저장된 boardList를 끄집어 낸다. -->
<%
	List<MemberDTO> memberList = (List)session.getAttribute("memberList");	//새롭게 memberList 선언
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>
	<h1> 회원 정보 목록 출력 페이지 </h1>
	<hr>
	
	<h3> 보안우선 <a href = "logout.do">Log-Out</a></h3>
	
	<!-- 검색기능추가 -->
	<form action = "getMemberList.jsp" method = "post">
		<table border = "1" cellsapcing = "0" cellpadding="0" width= "700px">
			<tr>
				<td>
					<select name = "searchCondition">
						<option value = "id">아이디</option>
						<option value = "name">이름</option>
					</select>
					<input type = "text" name = "searchKeyword"/>
					<input type = "submit" value = "검색">
				</td>
			</tr>
		</table>
	
		<table border = "1" cellspacing = "0" cellpadding = "0" width = "750px">
			<tr>
				<th bgcolor = "orange" width = "100px">번호</th>
				<th bgcolor = "orange" width = "150px">아이디</th>
				<th bgcolor = "orange" width = "150px">이름</th>
				<th bgcolor = "orange" width = "100px">나이</th>
				<th bgcolor = "orange" width = "150px">등록일</th>
				<th bgcolor = "orange" width = "100px">조회수</th>
			</tr>	
		<!-- for문 루프돌리면서 LIST의 DTO값을 끄집어 내며 출력 -->
		<%
			for(MemberDTO dto : memberList){
	//		for(MemberDTO dto :  List<MemberDTO> memberList = (List)session.getAttribute("memberList")){
	//		세션 임포트하지않고 바로 사용	
		%>
			<tr>
				<td align = "center"><%= dto.getIdx() %></td>
				<td><a href = "getMember.do?idx=<%= dto.getIdx() %>">
				<%= dto.getId() %>
				</a></td>
				<td align = "center"><%= dto.getName() %></td>
				<td align = "center"><%= dto.getAge() %></td>
				<td align = "center"><%= dto.getRegdate() %></td>
				<td align = "center"><%= dto.getCnt() %></td>
				<td></td>
			</tr>
		<%
			}
		%>
		</table>
		
		<p/>
		<a href = "insertMember.jsp"> 새정보 등록 </a>
	</form>

</center>
</body>
</html>