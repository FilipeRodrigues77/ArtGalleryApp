package locality;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegionList {

    public static void main(String[] args) {
        System.out.println(getAllRegion());
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/IAProj";
    private static final String DB_USER = "nuel";
    private static final String DB_PASSWORD = "testing12345";

    public static List<String> getAllRegion() {
        List<String> regions = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT regionName FROM region";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String region = resultSet.getString("regionName");
                    regions.add(region);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return regions;
    }
}
