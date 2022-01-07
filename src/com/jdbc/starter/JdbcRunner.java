package com.jdbc.starter;

import com.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        Class<Driver> driverClass = Driver.class;

        // DDL(CREATE,DROP,ALTER) -> executeUpdate
        // DML -> executeQuery этот метод используется для выполнения запросов только операторы SELECT
        String sql = """
                SELECT * FROM ticket;
                """;

        try (var connection = ConnectionManager.get();
            // Statement - этот интерфейс используется для доступа к БД для общих целей
             var statement = connection.createStatement();
        ) {
            System.out.println(connection.getSchema());
            System.out.println(connection.getTransactionIsolation());

            var executeResult = statement.executeQuery(sql);
            System.out.println(executeResult);
            while (executeResult.next()) {
                System.out.println(executeResult.getLong("id"));
                System.out.println(executeResult.getString("passenger_no"));
                System.out.println(executeResult.getBigDecimal("cost"));
                System.out.println("--------------- ");
            }
        }
    }
}
