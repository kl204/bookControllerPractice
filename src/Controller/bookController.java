package Controller;

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

import service.bookService;
import vo.bookVo;

public class bookController extends HttpServlet
{
    //분기에 따라서 url 조건을 바꾸도록 하고 포워딩 시킴
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");

        String cmd = req.getParameter("cmd");

        cmd = cmd == null?"list":cmd;
        String url = "./book_list.jsp";

        bookService bs = new bookService();
        boolean isRedirect = false;

        if(cmd.equals("list")){
            ArrayList<HashMap> bookvo = bs.searchBookAll();

            req.setAttribute("list", bookvo);
        }
        // C : Create
        else if (cmd.equals("regist")) {
            bookVo bookvo = new bookVo();
            bookvo.setIsbn(req.getParameter("book_isbn"));
            bookvo.setTitle(req.getParameter("book_title"));
            bookvo.setAuthor(req.getParameter("book_author"));
            bookvo.setBookPosition(req.getParameter("book_publisher"));
            String date = req.getParameter("book_published_date");
            System.out.println(date);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date now = df.parse(date);
                bookvo.setPublishDate(new Timestamp(now.getTime()));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                bs.registBook(bookvo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            url = "./bookController?cmd=list";
        }

        // R : Read
        else if (cmd.equals("view_regist")) {
            url = "./book_regist.jsp";
        } else if (cmd.equals("view_detail")) {
            String bookSeq = req.getParameter("book_seq");
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
            bookvo.setBookPosition(req.getParameter("book_publisher"));
            String date = req.getParameter("book_published_date");
            System.out.println(date);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date now = df.parse(date);
                bookvo.setPublishDate(new Timestamp(now.getTime()));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                bs.modifyBook(bookvo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            url = "./bookController?cmd=list";
        }

        // D : Delete
        else if (cmd.equals("remove")) {
            String bookSeq = req.getParameter("book_seq");
            String bookIsbn = req.getParameter("book_isbn");
            boolean flag = false;
            try {
                flag = bs.removeBook(Integer.parseInt(bookSeq),bookIsbn);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println(flag);
            url = "./bookController?cmd=list&flag=true";
            isRedirect = true;
        }

        if(!isRedirect) {
            RequestDispatcher rd = req.getRequestDispatcher(url);
            rd.forward(req, resp);
        } else {
            resp.sendRedirect(url);
        }

        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
