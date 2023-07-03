package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConnectionManager {

    private static ConnectionManager conn;

    private ConnectionManager(){

    }
    public static ConnectionManager getInstance() {
        if(conn==null){
            conn = new ConnectionManager();
        }
        return conn;
    }

    public Connection getConnection() {
        Connection con = null;
        Properties prop = new Properties();
        try {
            prop.load(new FileReader(new File("C:/Users/금정산2_PC12/IdeaProjects/bookControllerPractice/lib/db.properties")));

            String jdbcURL = prop.getProperty("jdbcURL");//"jdbc:mysql://localhost:3306/test";
            String driver = prop.getProperty("driver");//"com.mysql.cj.jdbc.Driver";
            String id = prop.getProperty("id");//"root";
            String pwd = prop.getProperty("pwd");//"1234";

            Class.forName(driver);
            con = DriverManager.getConnection(jdbcURL,id,pwd);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("class not found");
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("SQL exeception");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("filenotfound exeception");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("IOE exeception");
            throw new RuntimeException(e);
        }

//        System.out.println("exception 없음");
        return con;
    }



    public void closeConnection(ResultSet rs, Statement stmt, Connection con) {

        if(rs!=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(stmt!=null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(con!=null) {
            try {
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
