package dao;

import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import vo.bookVo;

public class bookDao {
    private ConnectionManager conUt;

    public bookDao(){
        conUt = ConnectionManager.getInstance();
    }


    // 도서 목록
    public ArrayList<HashMap> bookList() {

        Connection conn = conUt.getConnection();
        String sql = "select a.book_seq,b.book_isbn, b.book_title, b.book_author, b.book_publisher, b.book_published_date from book_copy a join book_info b on a.book_isbn = b.book_isbn order by a.book_seq asc;";

        ArrayList<HashMap> bookList= new ArrayList<>();

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

//                    System.out.println("next in");
                HashMap<String, Object> data = new HashMap<>();

                data.put("book_seq", rs.getInt("book_seq"));
                data.put("book_isbn", rs.getString("book_isbn"));
                data.put("book_title", rs.getString("book_title"));
                data.put("book_author", rs.getString("book_author"));
                data.put("book_publisher", rs.getString("book_publisher"));
                data.put("book_published_date", rs.getTimestamp("book_published_date"));

                bookList.add(data);

            }

            System.out.println(bookList);

            conUt.closeConnection(rs, pstmt, conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    //도서 등록
    public boolean bookRegist(bookVo bookvo) throws SQLException {

        boolean flagBookCopy = false;
        boolean flagBookInfo = false;

        Connection conn = conUt.getConnection();
        String bookInfoSql = "insert into book_info( book_isbn, book_title, book_author,book_publisher, book_published_date) values(?,?,?,?,?); ";
        String bookCopySql = "insert into book_copy (book_isbn) values (?);";

        // 순서1. book_info 등록
        try {
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(bookInfoSql);
            pstmt.setString(1,bookvo.getIsbn());
            pstmt.setString(2,bookvo.getTitle());
            pstmt.setString(3,bookvo.getAuthor());
            pstmt.setString(4,bookvo.getPublisher());
            pstmt.setTimestamp(5,bookvo.getPublishDate());

            //flag가 0이면 false sql에 행이 없다. 1이면 추가 성공
            if(pstmt.executeUpdate()==0){
                flagBookInfo = false;
            }else{
                flagBookInfo = true;
            }


        } catch (SQLException e) {
            System.out.println("book_info problem");
            e.printStackTrace();
            conn.rollback();
        }

        // 순서2. book_copy isbn 등록 -> 책 위치, 상태는 default, book_seq 자동생성
        try {
            PreparedStatement pstmt = conn.prepareStatement(bookCopySql);
            pstmt.setString(1,bookvo.getIsbn());

            //flag가 0이면 false sql에 행이 없다. 1이면 추가 성공
            if(pstmt.executeUpdate()==0){
                flagBookCopy = false;
            }else{
                flagBookCopy = true;
            }

        } catch (SQLException e) {
            System.out.println("book_copy problem");
            e.printStackTrace();
            conn.rollback();
        }finally {
            conn.setAutoCommit(true);
        }

        if(flagBookCopy!=false && flagBookInfo!=false){
            System.out.println("sql regist success");
            return true;
        }
        return false;
    }

    //도서 삭제
    public boolean bookDelete(int book_seq, String book_isbn) throws SQLException{
        boolean flagBookCopy = false;
        boolean flagBookInfo = false;

        Connection conn = conUt.getConnection();
        String bookInfoDeleteSql = "delete from book_info where book_isbn = ?;";
        String bookCopyDeleteSql = "delete from book_copy where book_seq = ?;";


        // 삭제 한다 너를
        try {
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(bookInfoDeleteSql);
            pstmt.setString(1, book_isbn);

            if(pstmt.executeUpdate()==0){
                flagBookInfo = false;
            }else {
                flagBookInfo = true;
            }


        } catch (SQLException e) {
            System.out.println("book_info problem");
            e.printStackTrace();
            conn.rollback();
        }

        // 순서2. book_copy isbn 등록 -> 책 위치, 상태는 default, book_seq 자동생성
        try {
            PreparedStatement pstmt = conn.prepareStatement(bookCopyDeleteSql);
            pstmt.setInt(1,book_seq);


            if(pstmt.executeUpdate()==0){
                flagBookCopy = false;
            }else {
                flagBookCopy = true;
            }


        } catch (SQLException e) {
            System.out.println("book_copy problem");
            e.printStackTrace();
            conn.rollback();
        }finally {
            conn.setAutoCommit(true);
            conUt.closeConnection(null,null,conn);
        }

        if(flagBookCopy!=false && flagBookInfo!=false){
            System.out.println("sql regist success");
            return true;
        }
        return false;

    }
    //도서 수정 페이지
    public HashMap<String, Object> bookUpdatePage (int book_seq) {
        Connection conn = conUt.getConnection();

        System.out.println(book_seq);

        String sql = "select a.book_seq, b.book_isbn, b.book_title, b.book_author, b.book_publisher, b.book_published_date," +
                "a.book_position, a.book_status from book_copy a join book_info b on a.book_isbn = b.book_isbn where b.book_seq=?;";
        HashMap<String, Object> data = new HashMap<>();

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(book_seq));

            ResultSet rs = pstmt.executeQuery();

            rs.next();
            System.out.print("bookUpdadatePage view : ");
            System.out.println(rs.getInt("book_seq"));

//            rs.next();
            data.put("book_seq", rs.getInt("book_seq"));
            data.put("book_isbn", rs.getString("book_isbn"));
            data.put("book_title", rs.getString("book_title"));
            data.put("book_author", rs.getString("book_author"));
            data.put("book_publisher", rs.getString("book_publisher"));
            data.put("book_published_date", rs.getDate("book_published_date"));
            data.put("book_position", rs.getString("book_position"));
            data.put("book_status", rs.getString("book_status"));

            System.out.print("data in dao : ");
            System.out.println(data);

            conUt.closeConnection(rs, pstmt, conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;

    }

    //도서 수정
    public boolean bookUpdate (bookVo bookvo) throws SQLException {
        boolean flagBookCopy = false;
        boolean flagBookInfo = false;

        Connection conn = conUt.getConnection();
        String bookInfoUpdateSql = "update book_info set book_isbn=?, book_title=?, book_author=?, book_publisher=?, book_published_date=? where book_isbn = ?";
        String bookCopyUpdateSql = "update book_copy set book_seq= ?, book_position= ?, book_status=? where book_seq = ?";


        // 순서1. book_info 등록
        try {
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(bookInfoUpdateSql);
            pstmt.setString(1, bookvo.getIsbn());
            pstmt.setString(2, bookvo.getTitle());
            pstmt.setString(3, bookvo.getAuthor());
            pstmt.setString(4, bookvo.getPublisher());
            pstmt.setTimestamp(5, bookvo.getPublishDate());
            pstmt.setString(6, bookvo.getIsbn());

            //flag가 0이면 false sql에 행이 없다. 1이면 추가 성공
            if(pstmt.executeUpdate()==0){
                flagBookInfo = false;
            }else {
                flagBookInfo = true;
            }

        } catch (SQLException e) {
            System.out.println("book_info problem");
            e.printStackTrace();
            conn.rollback();
        }

        // 순서2. book_copy isbn 등록 -> 책 위치, 상태는 default, book_seq 자동생성
        try {
            PreparedStatement pstmt = conn.prepareStatement(bookCopyUpdateSql);
            pstmt.setInt(1, bookvo.getBookSeq());
            pstmt.setString(2,bookvo.getBookPosition());
            pstmt.setString(3, bookvo.getBookStaus());
            pstmt.setInt(4, bookvo.getBookSeq());

            //flag가 0이면 false sql에 행이 없다. 1이면 추가 성공
            if(pstmt.executeUpdate()==0){
                flagBookCopy = false;
            }else {
                flagBookCopy = true;
            }


        } catch (SQLException e) {
            System.out.println("book_copy problem");
            e.printStackTrace();
            conn.rollback();
        }finally {
            conn.setAutoCommit(true);
        }

        if(flagBookCopy!=false && flagBookInfo!=false){
            System.out.println("sql regist success");
            return true;
        }
        return false;
    }



}
