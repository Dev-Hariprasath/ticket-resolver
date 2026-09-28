package com.hari.service;


import com.hari.dto.TicketRequest;
import com.hari.dto.TicketResponse;
import com.hari.model.Ticket;
import com.hari.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository repository;


    public TicketService(
            TicketRepository repository
    ) {

        this.repository = repository;
    }


    public Ticket createTicket(
            TicketRequest request
    ) {

        String id =
                "TCK-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0, 8)
                                .toUpperCase();


        Ticket ticket =
                new Ticket(

                        id,

                        request.getName(),

                        request.getCategory(),

                        request.getSubCategory(),

                        request.getPriority(),

                        request.getDescription()
                );


        return repository.save(ticket);
    }


    public List<Ticket> getAllTickets() {

        return repository.findAll();
    }


    public Ticket getTicket(String id) {

        return repository.findById(id);
    }


    public TicketResponse toResponse(
            Ticket ticket
    ) {

        return new TicketResponse(

                ticket.getId(),

                ticket.getName(),

                ticket.getCategory(),

                ticket.getSubCategory(),

                ticket.getPriority(),

                ticket.getDescription(),

                ticket.getStatus(),

                ticket.getAiDecision(),

                ticket.getResolution(),

                ticket.getKnowledgeArticleId(),

                ticket.getCreatedAt(),

                ticket.getUpdatedAt()
        );
    }


    public void updateTicket(
            Ticket ticket
    ) {

        ticket.setUpdatedAt(
                LocalDateTime.now()
        );

        repository.save(ticket);
    }
}
