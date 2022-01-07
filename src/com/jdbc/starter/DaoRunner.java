package com.jdbc.starter;

import com.jdbc.starter.dao.TicketDao;
import com.jdbc.starter.dto.TicketFilter;
import com.jdbc.starter.entity.Ticket;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class DaoRunner {
    public static void main(String[] args) {
        var ticketFilter = new TicketFilter(3 ,0,"Андрей Буйнов","C2");
        var ticketList = TicketDao.getInstance().findAll(ticketFilter);
        System.out.println(ticketList);

    }

    private static void updateTest() {
        var ticketDao = TicketDao.getInstance();
        var maybeTicket = ticketDao.findById(65L);
        System.out.println(maybeTicket);

        maybeTicket.ifPresent(ticket -> {
            ticket.setCost(BigDecimal.valueOf(180.25));
            ticketDao.update(ticket);
        });
    }

    private static void save() {
        var ticket = new Ticket();
        ticket.setPassengerNo("1234567");
        ticket.setPassengerName("TEST");
        ticket.setFlightId(3L);
        ticket.setCost(BigDecimal.ONE);
        ticket.setSeatNo("B3");
//        var savedTicket = ticketDao.save(ticket);
//        System.out.println(savedTicket);
    }
}
