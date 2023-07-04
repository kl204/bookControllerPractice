package Controller;

import service.bookService;
import vo.bookVo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class bookControllerOld extends HttpServlet
{
    //분기에 따라서 url 조건을 바꾸도록 하고 포워딩 시킴
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
// 1. 한글 req, resp 모두 인코딩 필수
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
// 2. 분기를 위한 cmd 받아오기 (list, regist, view_regist, view_detail, update, remove)
        String cmd = req.getParameter("cmd");
// 3. cmd default 정해줌
        cmd = cmd == null?"list":cmd;
// 4. bookController default는 list 페이지
        String url = "./book_list.jsp";
// 5. logic을 사용하기위한 서비스 객체 생성
        bookService bs = new bookService();
// 6. 리다이렉션 flag 생성
        boolean isRedirect = false;


// book_list 전체 도서 조회 : 전체책조회메서드, 메서드 결과 받을 객체, req.setAttribute("객체이름", 객체)
        if(cmd.equals("list")){
            ArrayList<HashMap> bookvo = bs.searchBookAll();

            req.setAttribute("list", bookvo);
        }


// C : Create = regist(등록):
        else if (cmd.equals("regist")) {

            try {
            ArrayList<HashMap> booklist = bs.searchBookAll();

            if(!booklist.get(0).get("book_isbn").equals(req.getParameter("book_isbn"))){

                bookVo bookvo = new bookVo();
                bookvo.setIsbn(req.getParameter("book_isbn"));
                bookvo.setTitle(req.getParameter("book_title"));
                bookvo.setAuthor(req.getParameter("book_author"));
                bookvo.setPublisher(req.getParameter("book_publisher"));
                String date = req.getParameter("book_published_date");

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date now = df.parse(date);
                    bookvo.setPublishDate(new Timestamp(now.getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                    bs.registBook(bookvo);
                url = "./bookController?cmd=list";

                }

            else{
                url = "./book_regist.jsp?notRegistFlag=true";
                System.out.println("you have been already same isbn book!");
            }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }



        }




// R : Read
        //리스트 페이지에서 그냥 등록페이지로 돌아갈때
        else if (cmd.equals("view_regist")) {
            url = "./book_regist.jsp";
            isRedirect = true;

        }

        //상세페이지 수정페이지에 데이터 올려놓을 때
        else if (cmd.equals("view_detail")) {
            String bookSeq = req.getParameter("bookSeq");
            HashMap<String, Object> copy = bs.findBook(Integer.parseInt(bookSeq));
            req.setAttribute("copy", copy);
            url = "./book_detail.jsp";
        }



// U : Update
        else if (cmd.equals("update")) {
            bookVo bookvo = new bookVo();
            bookvo.setIsbn(req.getParameter("book_isbn"));
            bookvo.setTitle(req.getParameter("book_title"));
            bookvo.setAuthor(req.getParameter("book_author"));
            bookvo.setPublisher(req.getParameter("book_publisher"));
            String date = req.getParameter("book_published_date");

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date now = df.parse(date);
                bookvo.setPublishDate(new Timestamp(now.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                isRedirect = false;
                bs.modifyBook(bookvo);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }




// D : Delete
        else if (cmd.equals("remove")) {
            boolean flag = false;

            String bookSeq = req.getParameter("book_seq");
            String bookIsbn = req.getParameter("book_isbn");

            try {

                flag = bs.removeBook(Integer.parseInt(bookSeq),bookIsbn);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            url = "./bookController?cmd=list&flag="+flag;
            isRedirect = true;
        }

//-------------------------------------------------------------------------------
//리다이렉션 체크
        if(!isRedirect) {
            RequestDispatcher rd = req.getRequestDispatcher(url);
            rd.forward(req, resp);
        } else {
            resp.sendRedirect(url);
        }

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
