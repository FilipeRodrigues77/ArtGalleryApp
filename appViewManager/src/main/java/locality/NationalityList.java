package locality;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NationalityList {

    public static void main(String[] args) {
        System.out.println(getAllNationality());
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/IAProj";
    private static final String DB_USER = "nuel";
    private static final String DB_PASSWORD = "testing12345";

    public static List<String> getAllNationality() {
        List<String> nationalities = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT nationalityName FROM nationality";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String nationality = resultSet.getString("nationalityName");
                    nationalities.add(nationality);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nationalities;
    }
}
