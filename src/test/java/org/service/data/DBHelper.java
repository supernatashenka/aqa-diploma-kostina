package org.service.data;

import lombok.SneakyThrows;
import lombok.val;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

public class DBHelper {

    private static QueryRunner runner = new QueryRunner();
    public static String expectedPaymentStatusApproved = "APPROVED";
    public static String expectedPaymentStatusDeclined = "DECLINED";

    private static Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }




    public static String getDebitCardStatus() throws SQLException {
        val statusSQL = "SELECT status FROM payment_entity;";
        String status = "";

        try (val statusStmt = connection().createStatement();) {

            try (val rs = statusStmt.executeQuery(statusSQL)) {
                if (rs.next()) {
                    status = rs.getString(1);
                }
            }
        }
        return status;
    }

    public static String getCreditStatus() throws SQLException {
        val statusSQL = "SELECT status FROM credit_request_entity;";
        String status = "";

        try (val statusStmt = connection().createStatement();) {

            try (val rs = statusStmt.executeQuery(statusSQL)) {
                if (rs.next()) {
                    status = rs.getString(1);
                }
            }
        }
        return status;
    }

    @SneakyThrows
    public static void cleanDatabase() {
        val connection = connection();
        runner.execute(connection, "DELETE FROM payment_entity;");
        runner.execute(connection, "DELETE FROM credit_request_entity;");
    }

}