package com.hari.repository;

import com.hari.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TicketRepository {

    private final Map<String, Ticket> tickets =
            new ConcurrentHashMap<>();


    public Ticket save(Ticket ticket) {

        tickets.put(ticket.getId(), ticket);

        return ticket;
    }


    public Ticket findById(String id) {

        return tickets.get(id);
    }


    public List<Ticket> findAll() {

        return new ArrayList<>(tickets.values());
    }


    public boolean existsById(String id) {

        return tickets.containsKey(id);
    }
}
