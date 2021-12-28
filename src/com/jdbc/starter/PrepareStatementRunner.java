package com.jdbc.starter;

import com.jdbc.starter.util.ConnectionManager;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PrepareStatementRunner {
    public static void main(String[] args) throws SQLException {
        Long flightId = 2L;

        var result = getTicketByFlightId(flightId);
        System.out.println(result);

        var result1 = getFlightsBetween(LocalDate.of(2020, 1, 1).atStartOfDay(), LocalDateTime.now());
        System.out.println(result1);

        checkMetaData();
    }

    private static void checkMetaData() throws SQLException {
        try (var connection = ConnectionManager.open()) {
            var metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                System.out.println(catalogs.getString(1));

                var schemas = metaData.getSchemas();
                while (schemas.next()) {
                    System.out.println(schemas.getString("TABLE_SCHEM"));
                }
                System.out.println();
                var tables = metaData.getTables(null, null, "%", null);
                while (tables.next()) {
                    System.out.println(tables.getString("TABLE_NAME"));
                }
            }
        }
    }

    private static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = """
                SELECT id
                FROM flight
                WHERE departure_date BETWEEN ? AND ?
                """;

        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(sql)) {
            System.out.println(prepareStatement);

            // установили ? на prepareStatement
            prepareStatement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(prepareStatement);
            prepareStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(prepareStatement);

            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                result.add(
                        resultSet.getObject(1, Long.class)
                );
            }
        }
        return result;

    }

    private static List<Long> getTicketByFlightId(Long flight) throws SQLException {

        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = ?
                """;

        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setFetchSize(50);
            prepareStatement.setQueryTimeout(10);
            prepareStatement.setLong(1, flight);

            // Этот интерфейс представляет результирующий набор базы данных
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getObject("id", Long.class));
            }
        }
        return result;
    }

}
