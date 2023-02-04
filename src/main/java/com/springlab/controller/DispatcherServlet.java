package com.springlab.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.springlab.member.MemberDAO;
import com.springlab.member.MemberDTO;

//@WebServlet("/DispatcherServlet")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DispatcherServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		process(request, response);
	}
	
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doget, dopost 메소드에서 받는 내용을 처리
		
		//1. Client의 요청 path 정보를 추출한다
		/*
		 * String url = request.getRequestURL().toString();
		 * System.out.println("url : "+url);
		 */
		
		String uri = request.getRequestURI();
		System.out.println("uri : "+ uri);
		
		String path = uri.substring(uri.lastIndexOf("/"));
		System.out.println("path : "+path);
		
		//2. Client의 요청 정보에 따라 적절하게 분기처리.
		if(path.equals("/login.do")) {
			//클라이언트가 /login.do를 요청하면
			//1. 비즈니스로직 처리 , dto , dao 객체 생성
			//2. 뷰 페이지로 전달 : *.jsp
			
			System.out.println("사용자 정보처리");
			System.out.println("/login.do 요청을 보냈습니다.");
			
			//1. 클라이언트가 보낸 변수의 값을 받아서 변수에 저장
			String id = request.getParameter("id");
			String pass = request.getParameter("pass");
			
			System.out.println("ID : "+id);
			System.out.println("PASS : "+pass);
			
			//2.변수값이 저장된 변수를 DTO에 setter 주입
			MemberDTO dto = new MemberDTO();
			dto.setId(id);
			dto.setPass(pass);
			
			//3.비즈니스로직을 처리하는 DAO에 DTO를 주입하여 비즈니스로직 처리
			MemberDAO user = new MemberDAO();
			MemberDTO userD = user.getUser(dto);
			
			//DB의 클라이언트에서 넘긴 ID/PW를 select해서 그 값을 DTO에 담아 리턴
			System.out.println(userD);
			
			//4. 백엔드 로직을 모두 처리 후 View페이지로 이동
			if(userD != null) {
				response.sendRedirect("getMemberList.do");
				System.out.println("아이디와 패스워드 일치함");
			}else {
				response.sendRedirect("login.jsp");
				System.out.println("아이디와 패스워드 불일치");
			}
			
		}else if(path.equals("/getMemberList.do")){
		
			MemberDAO dao = new MemberDAO();
	//		MemberDTO dto = new MemberDTO();
			
	//		List<MemberDTO> memberList = dao.getMemberList(dto);
			List<MemberDTO> memberList = dao.getMemberList();
			
			HttpSession session = request.getSession();
			
			session.setAttribute("memberList", memberList); //새롭게 memberList 선언
			
			/*
			 * HttpSession session = request.getSession();
			 * 
			 * session.setAttribute("memberList", dao.getMemberList()); // memberList 객체 생성없이 세션 사용
			 */			
			
			response.sendRedirect("getMemberList.jsp");
			
			
		}else if(path.equals("/insertMember.do")) {
			
		//	String idx = request.getParameter("idx");
			String id = request.getParameter("id");
			String pass = request.getParameter("pass");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String age = request.getParameter("age");
			String weight = request.getParameter("weight");
			
			MemberDTO dto = new MemberDTO();
			MemberDAO dao = new MemberDAO();
			
		//	dto.setIdx(Integer.parseInt(idx));
			dto.setId(id);
			dto.setPass(pass);
			dto.setName(name);
			dto.setEmail(email);
			dto.setAge(Integer.parseInt(age));
			dto.setWeight(Double.parseDouble(weight));
			
			dao.insertMember(dto);
			
			response.sendRedirect("getMemberList.do");
	
		}else if(path.equals("/getMember.do")) {
		
			String idx = request.getParameter("idx");
			
			MemberDTO dto = new MemberDTO();
			MemberDAO dao = new MemberDAO();
			
			dto.setIdx(Integer.parseInt(idx));
			
			MemberDTO member = dao.getMember(dto);
			
		// cnt 1씩 증가
			dao.viewCount(dto);
			
			
			HttpSession session = request.getSession();
			
			session.setAttribute("member", member);
			
			response.sendRedirect("getMember.jsp");
			
		}else if(path.equals("/updateMember.do")) {
			
			String pass = request.getParameter("pass");
			String email = request.getParameter("email");
			String idx = request.getParameter("idx");
			
			MemberDTO dto = new MemberDTO();
			MemberDAO dao = new MemberDAO();
			
			dto.setIdx(Integer.parseInt(idx));
			dto.setPass(pass);
			dto.setEmail(email);
			
			dao.updateMember(dto);
			
			response.sendRedirect("getMemberList.do");
			
		}else if(path.equals("/deleteMember.do")) {
			
			String idx = request.getParameter("idx");
			
			MemberDTO dto = new MemberDTO();
			MemberDAO dao = new MemberDAO();
			
			dto.setIdx(Integer.parseInt(idx));
			
			dao.deleteMember(dto);
			
			response.sendRedirect("getMemberList.do");
		}
	}

}
