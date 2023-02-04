package com.springlab.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.springlab.common.JDBCUtil;

@Repository("memberDAO")
public class MemberDAO {
	
	//JDBC관련 변수 선언
	private Connection conn = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//2. SQL쿼리를 상수 담아서 처리
	private final String MEMBER_INSERT = "insert into member(idx,id,pass,name,email,age,weight)"
										+ " values((select nvl(max(idx),0)+1 from member),?,?,?,?,?,?)";
	private final String MEMBER_UPDATE = "update member set pass=?, email=? where idx=?"; 
	private final String MEMBER_DELETE = "delete member where idx=?";
	private final String MEMBER_GET = "select * from member where idx =?";
	private final String MEMBER_LIST = "select * from member order by idx desc";
	private final String USER_GET = "select * from member where id = ? and pass = ? ";
	private final String VIEW_COUNT = "update member set cnt=((select cnt from member where idx=?)+1) where idx=?";
	
	//3. 메소드 insertmember() 리턴값 없음 void
			// getmember() : BoardDTO 에 담아서 전송 , 가져온 레코드가 1개 
			// getmemberList() : 각 각의 레코드를 DTO (1개) , ArrayList에 DTO 객체를 담아서 리턴 
	
	
	
	//3-1. 회원정보 등록 처리하는 메소드 : insertmember()
	public void insertMember(MemberDTO dto) {
		System.out.println("--> JDBC로 insertmember() 기능 처리 시작");
		
		try { //try ~ catch : 오류발생해도 프로그램이 꺼지지 않도록 예외발생
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_INSERT);
			
			pstmt.setString(1,dto.getId());
			pstmt.setString(2,dto.getPass());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4,dto.getEmail());
			pstmt.setInt(5,dto.getAge());
			pstmt.setDouble(6,dto.getWeight());
			
			pstmt.executeUpdate();
			
			System.out.println("--> JDBC로 insertmember() 기능 처리 완료");
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("--> JDBC로 insertmember() 기능 처리 실패");
			
		}finally {
			JDBCUtil.close(pstmt, conn);
			System.out.println("모든 객체가 close() 되었습니다.");
		}		
	}

	//3-2. 회원정보 상세 조회 처리 메소드 : getMember()
	public MemberDTO getMember(MemberDTO dto) {
		System.out.println("--> JDBC로 getmember() 기능 처리 시작");
		
		//리턴 돌려줄 변수 선언 
		MemberDTO member = new MemberDTO();
		
		try {
			//객체생성
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_GET);
			
			//select * from member where idx =?
			pstmt.setInt(1,dto.getIdx());
			
			//DB에서 select해온 값을 rs에 저장함
			rs = pstmt.executeQuery();
			
			//rs에 담긴 값을 DTO에 저장해서 리턴으로 돌려줌
			if(rs.next()) {
				member.setIdx(rs.getInt("IDX"));
				member.setId(rs.getString("ID"));
				member.setPass(rs.getString("PASS"));
				member.setName(rs.getString("NAME"));
				member.setEmail(rs.getString("EMAIL"));
				member.setAge(rs.getInt("AGE"));
				member.setWeight(rs.getDouble("WEIGHT"));
				member.setRegdate(rs.getDate("REGDATE"));
				member.setCnt(rs.getInt("CNT"));
				
			}else {
				System.out.println("찾으시는 레코드 값이 없습니다.");
			}
			System.out.println("--> JDBC로 getmember() 기능 처리 완료");
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("--> JDBC로 getmember() 기능 처리 실패");
		}finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return member;
	}

		
	
	  //3-3. 글 목록 처리 메소드 : getMemberList() : 많은 레코드 
//	public List<MemberDTO> getMemberList(MemberDTO dto) {
	public List<MemberDTO> getMemberList() {
		System.out.println("--> JDBC로 getMemberList() 기능 처리 시작");
		
		//리턴 돌려줄 변수 선언 :List
		
		List<MemberDTO> memberList = new ArrayList<MemberDTO>();
		MemberDTO member;
		
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_LIST);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					member = new MemberDTO();
					
					member.setIdx(rs.getInt("IDX"));
					member.setId(rs.getString("ID"));
					member.setPass(rs.getString("PASS"));
					member.setName(rs.getString("NAME"));
					member.setEmail(rs.getString("EMAIL"));
					member.setAge(rs.getInt("AGE"));
					member.setWeight(rs.getDouble("WEIGHT"));
					member.setRegdate(rs.getDate("REGDATE"));
					member.setCnt(rs.getInt("CNT"));
					
					//memberList : ArrayList에 add() 메소드를 사용해서 member(DTO) 저장
					memberList.add(member);
					
				}while(rs.next());
			}else {
				System.out.println("테이블에 레코드가 비어있습니다.");
			}
			System.out.println("--> JDBC로 getMemberList() 기능 처리 완료");
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("--> JDBC로 getMemberList() 기능 처리 실패");			
		}finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return memberList;
	 }
	 
	public MemberDTO getUser(MemberDTO dto) {
		
		MemberDTO user = null;
		
		System.out.println("DAO - "+dto.getId());
		System.out.println("DAO - "+dto.getPass());
		
		try {
			System.out.println("--> JDBC로 getUser() 시작");
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(USER_GET);
			
			//pstmt 할당된 쿼리 물음표에 변수값 할당 후 실행
			pstmt.setString(1,dto.getId());
			pstmt.setString(2,dto.getPass());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				user = new MemberDTO();
				
				System.out.println("DB에서 값이 select되었습니다.");
				
				user.setIdx(rs.getInt("IDX"));
				user.setId(rs.getString("ID"));
				user.setPass(rs.getString("PASS"));
				user.setName(rs.getString("NAME"));
				user.setEmail(rs.getString("EMAIL"));
				user.setAge(rs.getInt("AGE"));
				user.setWeight(rs.getDouble("WEIGHT"));
				user.setRegdate(rs.getDate("REGDATE"));
				user.setCnt(rs.getInt("CNT"));
				
				System.out.println("JDBC로 DB를 담아와서 DTO로 전송");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("JDBC로 쿼리 실행 중 오류발생");
			
		}finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return user;
	}
	
	public void updateMember(MemberDTO dto) {
		
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_UPDATE);
			
		//	update member set pass=?, email=? where idx=?"
			
			pstmt.setString(1,dto.getPass());
			pstmt.setString(2,dto.getEmail());
			pstmt.setInt(3, dto.getIdx());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}finally {
			JDBCUtil.close(pstmt,conn);
		}
	}
			
	public void deleteMember(MemberDTO dto) {
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_DELETE);
			pstmt.setInt(1, dto.getIdx());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(pstmt, conn);
		}
	}
			
	
	public void viewCount(MemberDTO dto) {		
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(VIEW_COUNT);
	//VIEW_COUNT = "update member set cnt=((select cnt from member where idx=?)+1) where idx=?";		
			pstmt.setInt(1,dto.getIdx());
			pstmt.setInt(2,dto.getIdx());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
			
			
			
			
			
			

}















