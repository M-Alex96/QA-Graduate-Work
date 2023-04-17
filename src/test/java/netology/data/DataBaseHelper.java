package netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseHelper {

    private static QueryRunner runner = new QueryRunner();

    public static Connection getConn() throws SQLException {
        String url = System.getProperty("db.url");
        String username = System.getProperty("db.user");
        String password = System.getProperty("db.password");
        return DriverManager.getConnection(url, username, password);
    }

    @SneakyThrows
    public static String getCardPaymentEntity() {
        try (var conn = getConn();
             var countStmt = conn.createStatement()) {
            var codeSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
            var resultSet = countStmt.executeQuery(codeSQL);
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }


    @SneakyThrows
    public static String getCreditEntity() {
        try (var conn = getConn();
             var countStmt = conn.createStatement()) {
            var codeSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
            var resultSet = countStmt.executeQuery(codeSQL);
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static void cleanDataBase() {
        var conn = getConn();
        runner.execute(conn, "DELETE FROM credit_request_entity");
        runner.execute(conn, "DELETE FROM order_entity");
        runner.execute(conn, "DELETE FROM payment_entity");
    }
}
