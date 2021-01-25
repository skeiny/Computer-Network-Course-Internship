package server.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dao {
    //获取连接
    Connection conn = DbUtil.getConnection();

    //增加
    public void add(MyUser user) throws SQLException {
        String sql = "INSERT INTO `user`(fName, fPassword) values(?, ?)";

        PreparedStatement ptmt = conn.prepareStatement(sql); //预编译SQL，减少sql执行

        ptmt.setString(1, user.getFName());
        ptmt.setString(2, user.getFPassword());

        ptmt.execute();
    }

    public boolean add(String fName, String fPassword) throws SQLException {
        boolean flag = true;
        String sql1 = "select fName from `user`";
        String sql2 = "INSERT INTO `user`(fName, fPassword) values(?, ?)";

        PreparedStatement ptmt1 = conn.prepareStatement(sql1);
        ResultSet rs = ptmt1.executeQuery();
        while (rs.next()) {
            if (fName.equals(rs.getString("fName"))){
                flag = false;
                break;
            }
        }
        if (flag) {
            PreparedStatement ptmt2 = conn.prepareStatement(sql2);
            ptmt2.setString(1, fName);
            ptmt2.setString(2, fPassword);

            ptmt2.execute();
            return true;
        } return false;
    }

    public void del(MyUser user) throws SQLException {
        Connection conn = DbUtil.getConnection();

        String sql = "delete from `user` where fName = ?";

        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, user.getFName());

        ptmt.execute();
    }

    public boolean get(MyUser user) throws SQLException {
        Connection conn = DbUtil.getConnection();

        String sql = "select fPassword from `user` where fName = ?";

        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, user.getFName());

        ResultSet rs = ptmt.executeQuery();
        String password = "";
        while (rs.next()) {
            password = rs.getString("fPassword");
        }
        return password.equals(user.getFPassword());
    }

    public boolean get(String fName, String fPassword) throws SQLException {
        Connection conn = DbUtil.getConnection();

        String sql = "select fPassword from `user` where fName = ?";

        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, fName);

        ResultSet rs = ptmt.executeQuery();
        String password;
//        while (rs.next()) {
//            System.out.println(rs.getString("fPassword"));
//        }
        if (rs.next()) {
            password = rs.getString("fPassword");
        } else {
            return false;
        }
        return fPassword.equals(password);
    }

}
