package lt.bit.prekes.dataOLD;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrekeRepo {
    private static List<Preke> prekes = null;

    public static void loadPrekesFromDB(int cekis_id,Connection conn) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from Prekes WHERE `cekis_id`=?");) {
            preparedStatement.setInt(1, cekis_id);
            ResultSet rs = preparedStatement.executeQuery();
            prekes = new ArrayList<>();
            while (rs.next()) {
                Preke preke = new Preke(rs.getInt("id"), rs.getInt("cekis_id"), rs.getString("preke"),
                        rs.getDouble("kiekis"), rs.getDouble("kaina"), rs.getInt("tipas_id"));
                prekes.add(preke);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Preke> getPrekes(int cekis_id,Connection conn) throws SQLException {
        loadPrekesFromDB(cekis_id,conn);
        return prekes;
    }

    public static void addPreke(Preke preke, Connection conn) throws SQLException {
        if (preke != null) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO `Prekes`.`Prekes` (`cekis_id`,`preke`,`kiekis`,`kaina`,`tipas_id`) VALUES (?,?,?,?,?);")) {
                preparedStatement.setInt(1, preke.getCekis_id());
                preparedStatement.setString(2, preke.getPreke());
                preparedStatement.setDouble(3, preke.getKiekis());
                preparedStatement.setDouble(4, preke.getKaina());
                preparedStatement.setInt(5, preke.getTipas_id());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void deletePreke(int id, Connection conn) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM `Prekes`.`Prekes` WHERE id=?;")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updatePreke(Preke preke, Connection conn) throws SQLException {
        if (preke != null) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE `Prekes`.`Prekes` SET `preke` = ?, `kiekis` = ?, `kaina` = ?, `tipas_id` = ?  WHERE `id` = ?;")) {
                preparedStatement.setString(1, preke.getPreke());
                preparedStatement.setDouble(2, preke.getKiekis());
                preparedStatement.setDouble(3, preke.getKaina());
                preparedStatement.setInt(4, preke.getTipas_id());
                preparedStatement.setInt(5, preke.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Double sumaPagalCeki(int cekis_id, Connection conn) throws SQLException {
        double suma = 0;
        try (PreparedStatement preparedStatement = conn.prepareStatement("select sum(`kiekis`*`kaina`) as suma  from `Prekes`.`Prekes` WHERE `cekis_id`=?");) {
            preparedStatement.setInt(1, cekis_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                suma = (rs.getDouble("suma"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return suma;
    }

    public static Double sumaPagalDatas(Connection conn, java.util.Date data_nuo, java.util.Date data_iki) throws SQLException {
        double suma = 0;
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "select sum(`Prekes`.`kiekis`*`Prekes`.`kaina`) as suma from Cekis, Prekes  WHERE Cekis.id=Prekes.cekis_id AND (Cekis.data >= ? AND Cekis.data <= ?);")) {
            preparedStatement.setDate(1, new Date(data_nuo.getTime()));
            preparedStatement.setDate(2, new Date(data_iki.getTime()));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                suma = (rs.getDouble("suma"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return suma;
    }

    public static Map<String,Double> mapPagalDatasTipus(Connection conn, java.util.Date data_nuo, java.util.Date data_iki) throws SQLException {
        Map<String,Double> map = new HashMap<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "select Tipas.pavadinimas as tipas, sum(Prekes.kiekis*Prekes.kaina) as suma  from Cekis, Prekes, Tipas\n" +
                        " WHERE Cekis.id=Prekes.cekis_id AND Prekes.tipas_id=Tipas.id \n" +
                        " AND (Cekis.data >= ? AND Cekis.data <= ?) GROUP BY Tipas.pavadinimas;")) {
            preparedStatement.setDate(1, new Date(data_nuo.getTime()));
            preparedStatement.setDate(2, new Date(data_iki.getTime()));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("tipas"),rs.getDouble("suma"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    public static Double visaSumaPagal(Connection conn) throws SQLException {
        double suma = 0;
        try (PreparedStatement preparedStatement = conn.prepareStatement("select sum(`kiekis`*`kaina`) as suma  from `Prekes`.`Prekes`");) {
           // preparedStatement.setInt(1, cekis_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                suma = (rs.getDouble("suma"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return suma;
    }


    public static Preke findPreke(int id, Connection conn) throws SQLException {
        Preke preke = null;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select * from Prekes");) {
            prekes = new ArrayList<>();
            while (rs.next()) {
                preke = new Preke(rs.getInt("id"), rs.getInt("cekis_id"), rs.getString("preke"),
                        rs.getDouble("kiekis"), rs.getDouble("kaina"), rs.getInt("tipas_id"));
                prekes.add(preke);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return preke;
    }

}
