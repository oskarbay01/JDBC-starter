package com.jdbc.starter;

import com.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRunner {
    public static void main(String[] args) throws SQLException {
        long flightId = 9;
        String deleteFlightSql = """
                DELETE FROM flight
                WHERE id =?
                """;
        String deleteTicketsql = """
                DELETE FROM flight
                WHERE id =?
                """;

        Connection connection = null;
        PreparedStatement deleteFlightStatement = null;
        PreparedStatement deleteTicketStatement = null;
        try {
            connection = ConnectionManager.open();
            deleteFlightStatement = connection.prepareStatement(deleteFlightSql);
            deleteTicketStatement = connection.prepareStatement(deleteTicketsql);

            // transaction доступ
            connection.setAutoCommit(false);

            deleteFlightStatement.setLong(1, flightId);
            deleteTicketStatement.setLong(1, flightId);

            deleteFlightStatement.executeUpdate();
            deleteTicketStatement.executeUpdate();

            //
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (deleteTicketStatement != null) {
                deleteTicketStatement.close();
            }
            if (deleteFlightStatement != null) {
                deleteFlightStatement.close();
            }
        }
    }
}
