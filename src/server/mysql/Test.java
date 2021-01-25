package server.mysql;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) {
        Dao dao = new Dao();
        try {
//            dao.add("zhl4", "789");
            System.out.println(dao.get("zhl3", "789"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
