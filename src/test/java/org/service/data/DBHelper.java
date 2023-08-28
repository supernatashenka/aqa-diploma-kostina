package org.service.data;

import lombok.SneakyThrows;
import lombok.val;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;


public class DBHelper {

    private static QueryRunner runner = new QueryRunner();
    public static String expectedPaymentStatusApproved = "APPROVED";
    public static String expectedPaymentStatusDeclined = "DECLINED";

    private static String url = System.getProperty("db.url");
    private static String user = System.getProperty("db.user");
    private static String password = System.getProperty("db.password");

    @SneakyThrows
    public static String getDebitCardStatus() {
        QueryRunner runner = new QueryRunner();
        String dataSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        String status = null;
        try (
                Connection connection = DriverManager.getConnection(
                        url, user, password
                )
        ) {
            status = runner.query(connection, dataSQL, new ScalarHandler<>());
        }
        return status;
    }

    @SneakyThrows
    public static String getCreditStatus() {
        QueryRunner runner = new QueryRunner();
        String dataSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        String status = null;
        try (
                Connection connection = DriverManager.getConnection (
                        url, user, password
                )
        ) {
            status = runner.query(connection, dataSQL, new ScalarHandler<>());
        }
        return status;
    }

    @SneakyThrows
    public static void cleanDatabase() throws SQLException {
        val runner = new QueryRunner();
        val conn = DriverManager.getConnection(url, user, password);
        runner.update(conn, "DELETE FROM credit_request_entity;");
        runner.update(conn, "DELETE FROM payment_entity;");
        runner.update(conn, "DELETE FROM order_entity;");
    }
}
