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

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String cmd = req.getParameter("cmd");
        cmd = cmd== null ?"list" : cmd;

        String url = "./book_list.jsp";

        bookService bs = new bookService();

        boolean isRedirect = false;

        //list, regist, view_datail, view_regist, update, remove

        if(cmd.equals("list")){
            ArrayList<HashMap> list = bs.searchBookAll();

            req.setAttribute("list", list);
        }

        if(cmd.equals("regist")){

            ArrayList<HashMap> list = bs.searchBookAll();

            boolean isIsbnExist = false;
            for(HashMap lst : list){
                if(lst.get("book_isbn").equals(req.getParameter("book_isbn"))){
                    isIsbnExist = true;
                    break;
                }
            }

            if(!isIsbnExist){
                bookVo bookvo = new bookVo();
                bookvo.setIsbn(req.getParameter("book_isbn"));


            }

        }

        if(cmd.equals("view_datail")){
            url = "./book_datail.jsp";
        }

        if(cmd.equals("view_regist")){

        }

        if(cmd.equals("update")){

        }

        if(cmd.equals("remove")){

        }

        //-----------------------------------------------

        if(!isRedirect){
            RequestDispatcher rd = req.getRequestDispatcher(url);
            rd.forward(req,resp);
        }else{
            resp.sendRedirect(url);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
