package lt.bit.prekes.dataOLD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipasRepo {
    private static List<Tipas> tipai = null;


    public static void loadTipaiFromDB(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from Tipas")){
            if (stmt != null) {
                tipai = new ArrayList<>();
                while (rs.next()) {
                    Tipas tipas = new Tipas(rs.getInt("id"), rs.getString("Pavadinimas"));
                    tipai.add(tipas);
                }
                rs.close();
            } else {
                System.out.println("No connection to DB");
            }
        }
    }

    public static List<Tipas> getTipai(Connection conn) throws SQLException {
        loadTipaiFromDB(conn);
        return tipai;
    }

    public static void addTipas(Tipas tipas, Connection conn) throws SQLException {
        if (tipas != null) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO `Prekes`.`Tipas` (`Pavadinimas`) VALUES (?);");) {
                preparedStatement.setString(1, tipas.getPavadinimas());
            preparedStatement.executeUpdate();
            }
        }
    }

    public static void deleteTipas(Tipas tipas, Connection conn) throws SQLException {
        if (tipas != null) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM `Prekes`.`Tipas` WHERE id=?;");) {
                preparedStatement.setInt(1, tipas.getId());
            preparedStatement.executeUpdate();
            }
        }
    }

    public static void updateTipas(Tipas tipas, Connection conn) throws SQLException {
        if (tipas != null) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE `Prekes`.`Tipas` SET `Pavdinimas` WHERE `id` = ?;")) {
                preparedStatement.setString(1, tipas.getPavadinimas());
                preparedStatement.setInt(2, tipas.getId());
            preparedStatement.executeUpdate();
            }
        }
    }

    public static void findTipas(Tipas tipas, Connection conn) throws SQLException {
        if (tipas != null) {
            String SQL = "SELECT * FROM `Prekes`.`Tipas` WHERE Pavadinimas LIKE ? or Id LIKE ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
            preparedStatement.setString(1, tipas.getPavadinimas());
            preparedStatement.setInt(2, tipas.getId());
            preparedStatement.executeUpdate();
        }
    }

}
