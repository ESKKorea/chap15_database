package com.javalab.school.execution;

import java.sql.*;
import java.util.Scanner;

/**
 * 학생 데이터를 등록하는 클래스
 */
public class StudentInsert {
    // 오라클 DB에 접속해서 하기 위한 정보
    public static void main(String[] args) {
        //오라클 DB에 접속하기 위한 커넥션 객체
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);

        try {
            //JDBC 드라이버 로딩(요라클 ojbdc7.jar 파일 필요)
            //JDBC 드라이버가 있어야 자바 프로그램에서 오라클 데이터베이스 접속 할 수 있음.
            //오라클 드라이버를 로딩하면 JDBC 드라이버가 DriverManager에 등록됨
            Class.forName("oracle.jdbc.OracleDriver");

            //오라클 DB에 접속하기 위한 커넥션 객체 열기
            //DriverManager에게서 커넥션을 요청해서 얻어내야 한다.
            //오라클 데이터베이스 접속 문자열 :jdbc:oracle:thin@localhost:1521:orcl
            //user : 오라클 사용자 계정
            //password : 오라클 사용자 계정 비밀번호

            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");
            System.out.println("DB 접속 성공");

            registerStudent(conn, scanner);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } // end of main

    private static void registerStudent(Connection conn, Scanner scanner) {
        System.out.println("[새 학생 입력]");
        System.out.print("학생 ID: ");
        String id = scanner.nextLine();
        System.out.print("주민번호: ");
        String jumin = scanner.nextLine();
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.print("학년: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("주소: ");
        String address = scanner.nextLine();
        System.out.print("학과 코드: ");
        int department = scanner.nextInt();
        scanner.nextLine();

        String sql = "INSERT INTO student (student_id, jumin, name, year, address, department_id) " +
                " VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try  {
            //커넥션 객체로부터 PreparedStatement 객체를 얻어냄
            //PreparedStatement.prepareStatement (실행할 쿼리문) : 쿼리 실행
            pstmt = conn.prepareStatement(sql);
            //쿼리문의 ? 자리에 값을 채워넣음
            pstmt.setString(1, id);
            pstmt.setString(2, jumin);
            pstmt.setString(3, name);
            pstmt.setInt(4, year);
            pstmt.setString(5, address);
            pstmt.setInt(6, department);
            //쿼리문의 파라미터인 ? 를 채운 후 쿼리 실행
            pstmt.executeUpdate(); // 쿼리 실행 /저장,수정,삭제는 excuteUpdate() 메소드 사용
            System.out.println("학생이 성공적으로 등록되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            scanner.close();
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
