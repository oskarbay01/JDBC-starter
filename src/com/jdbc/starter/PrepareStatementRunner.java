package com.jdbc.starter;

import com.jdbc.starter.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrepareStatementRunner {
    public static void main(String[] args) throws SQLException {
        Long flightId = 2L;

        var result = getTicketByFlightId(flightId);
        System.out.println(result);
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
