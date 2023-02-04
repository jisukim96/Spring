<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "com.springlab.member.MemberDTO" %> 

<%
	MemberDTO member = (MemberDTO) session.getAttribute("member");
%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 글 상세 페이지 </title>
</head>
<body>
<center>
	<h1> 회원 정보 상세페이지 </h1>
	<a href = "logout.do">Log-Out</a>
	<hr>
	
	<form action = "updateMember.do" method = "post">
	<input type = "hidden" name = "idx" value="<%=member.getIdx() %>">
		<table border = "1" cellspcing ="0" cellpadding="0">
			<tr>
				<td bgcolor="orange" width="70">아이디</td>
				<td><%= member.getId() %></td>
			</tr>
			<tr>
				<td bgcolor="orange" width="70">비밀번호</td>
				<td><input type="password" name = "pass" value ="<%= member.getPass() %>"></td>
			</tr>
			<tr>
				<td bgcolor="orange" width="70">이름</td>
				<td><%= member.getName() %></td>
			</tr>
			<tr>
				<td bgcolor="orange" width="70">이메일</td>
				<td><input type="text" name = "email" value ="<%= member.getEmail() %>"></td>
			</tr>
			<tr>
				<td bgcolor="orange" width="70">나이</td>
				<td><%= member.getAge() %></td>
			</tr>
			<tr>
				<td bgcolor="orange" width="70">체중</td>
				<td><%= member.getWeight() %></td>
			</tr>
			<tr>
				<td colspan ="2" align = "right"><input type="submit" value="정보 수정"></td>
			</tr>
		</table>
	</form>
	<p/>
	<a href = "insertMember.jsp"> 글 등록</a> &nbsp;&nbsp;&nbsp;
	<a href = "deleteMember.do?idx=<%= member.getIdx() %>"> 글 삭제 </a>&nbsp;&nbsp;&nbsp;
	<a href = "getMemberList.do">글 목록</a>

</center>

</body>
</html>