package lt.bit.prekes.dataOLD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CekisRepo {
    private static List<Cekis> cekiai = null;

    public static void loadCekiaiFromDB(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from Cekis");) {
                cekiai = new ArrayList<>();
                while (rs.next()) {
                    Cekis cekis = new Cekis(rs.getInt("id"), rs.getDate("Data"), rs.getString("Parduotuve"),
                            rs.getString("Aprasymas"));
                    cekiai.add(cekis);
                }
        }
    }

    public static List<Cekis> getCekiai(Connection conn) throws SQLException {
            loadCekiaiFromDB(conn);
        return cekiai;
    }

    public static List<Cekis> getCekiaiPagalData(Connection conn,  java.util.Date data_nuo, java.util.Date data_iki) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Cekis WHERE Cekis.data >= ? AND Cekis.data <= ?;")) {
            preparedStatement.setDate(1, new Date(data_nuo.getTime()));
            preparedStatement.setDate(2, new Date(data_iki.getTime()));
            ResultSet rs = preparedStatement.executeQuery();
            cekiai = new ArrayList<>();
            while (rs.next()) {
                Cekis cekis = new Cekis(rs.getInt("id"), rs.getDate("Data"), rs.getString("Parduotuve"),
                        rs.getString("Aprasymas"));
                cekiai.add(cekis);
            }
        }
        return cekiai;
    }


    public static void addCekis(Cekis cekis, Connection conn) throws SQLException {
        if (cekis != null) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO `Prekes`.`Cekis` (`Data`,`Parduotuve`,`Aprasymas`) VALUES (  ?, ?, ?);")) {
                preparedStatement.setDate(1, new Date(cekis.getData().getTime()));
                preparedStatement.setString(2, cekis.getPavadinimas());
                if (!"".equals(cekis.getAprasymas())) {
                    preparedStatement.setString(3, cekis.getAprasymas());
                } else {
                    preparedStatement.setNull(3, Types.VARCHAR);
                }
                preparedStatement.executeUpdate();
            }
        }
    }

    public static void deleteCekis(int id, Connection conn) throws SQLException {
                try(PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM `Prekes`.`Cekis` WHERE id=?;")) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
        }
    }

    public static void updateCekis(Cekis cekis, Connection conn) throws SQLException {
        if (cekis != null) {
           try(PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE `Prekes`.`Cekis` SET `Data` = ?, `Parduotuve` = ?, `Aprasymas` = ?  WHERE `id` = ?;")) {
               preparedStatement.setDate(1,  new Date(cekis.getData().getTime()));
               preparedStatement.setString(2, cekis.getPavadinimas());
               preparedStatement.setString(3, cekis.getAprasymas());
               preparedStatement.setInt(4, cekis.getId());
               preparedStatement.executeUpdate();
           }
        }
    }

    public static void findCekis(Cekis cekis, Connection conn) throws SQLException {
        if (cekis != null) {
            String SQL = "INSERT INTO `Prekes`.`Cekis` (`Data`,`Parduotuve`,`Aprasymas`) VALUES ( ? , ?, ?, ?);";
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
            preparedStatement.setDate(1, (Date) cekis.getData());
            preparedStatement.setString(2, cekis.getPavadinimas());
            preparedStatement.setString(3, cekis.getAprasymas());
            preparedStatement.executeUpdate();
        }
    }

}
