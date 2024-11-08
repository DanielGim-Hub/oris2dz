import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "qwe123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/oris2dz";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from driver");

        while (result.next()) {
            System.out.println(result.getInt("id") + " " + result.getString("name") + " " + result.getString("surname") + " " + result.getString("age"));
        }

        // Добавление 6 пользователей единым запросом
        String sqlInsertUser = "insert into driver(name, surname, age) " +
                "values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);

        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 6; i++) {
            System.out.println("Введите имя, фамилию и возраст для пользователя под номером" + (i + 1) + ":");
            String firstName = scanner.nextLine();
            String secondName = scanner.nextLine();
            String age = scanner.nextLine();

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, secondName);
            preparedStatement.setString(3, age);
            preparedStatement.addBatch();
        }

        int[] affectedRows = preparedStatement.executeBatch();
        System.out.println("Добавлено строк: " + affectedRows.length);

        // Вывод водителей, которым больше 25 лет
        String sqlSelectOldDrivers = "SELECT * FROM driver WHERE CAST(age AS INTEGER) > 25";
        ResultSet oldDriversResult = statement.executeQuery(sqlSelectOldDrivers);
        System.out.println("Водители старше 25 лет:");
        while (oldDriversResult.next()) {
            System.out.println(oldDriversResult.getInt("id") + " " + oldDriversResult.getString("name") + " " + oldDriversResult.getString("surname") + " " + oldDriversResult.getString("age"));
        }
    }
}
