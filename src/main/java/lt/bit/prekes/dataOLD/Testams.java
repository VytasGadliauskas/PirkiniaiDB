package lt.bit.prekes.dataOLD;

import java.sql.*;
import java.util.List;

public class Testams {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String URL = "jdbc:mysql://localhost:3306/Prekes";
        String USER = "user";
        String PASS = "user123";

        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        List<Tipas> tipai = TipasRepo.getTipai(conn);
        for (Tipas tipas: tipai) {
            System.out.println(tipas);
        }

    }
}
