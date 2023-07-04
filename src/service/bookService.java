package service;

import dao.bookDao;
import vo.bookVo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class bookService {
    private bookDao dao;
    public bookService(){
        dao = new bookDao();
    }



    public boolean registBook(bookVo copy) throws SQLException {
        boolean flag = false;
        flag = dao.bookRegist(copy);
        return flag;
    }
    public ArrayList<HashMap> searchBookAll(){
        ArrayList<HashMap> list = null;
        list = dao.bookList();
        return list;
    }
    public boolean removeBook(int bookSeq, String book_isbn) throws SQLException {
        // TODO Auto-generated method stub
        boolean flag = false;
        flag = dao.bookDelete(bookSeq, book_isbn);
        return flag;
    }
    public HashMap<String, Object> findBook(int bookSeq) {
        HashMap<String, Object> list = null;
        list = dao.bookUpdatePage(bookSeq);
        System.out.print("list in bookService = ");
        System.out.println(list);
        return list;
    }
    public boolean modifyBook(bookVo copy) throws SQLException {
        // TODO Auto-generated method stub
        boolean flag = false;
        flag = dao.bookUpdate(copy);
        return flag;
    }


}
