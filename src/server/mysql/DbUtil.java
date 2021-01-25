package server.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

    public static final String URL = "jdbc:mysql://139.199.66.139:3308/user?serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD = "SCAUdachuang@database12138.";
    private static Connection conn = null;
    static{
        try {
            //1.加载驱动程序
            //Class.forName("com.mysql.cj.jdbc.Driver");//hl的版本
            Class.forName("com.mysql.jdbc.Driver");//ky的版本
            //2. 获得数据库连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return conn;
    }
}