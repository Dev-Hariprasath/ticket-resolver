package com.hari.controller;


import com.hari.dto.TicketRequest;
import com.hari.dto.TicketResponse;
import com.hari.model.Ticket;
import com.hari.service.TicketAnalysisService;
import com.hari.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketAnalysisService ticketAnalysisService;

    public TicketController(
            TicketService ticketService,
            TicketAnalysisService ticketAnalysisService
    ) {
        this.ticketService = ticketService;
        this.ticketAnalysisService = ticketAnalysisService;
    }

    @PostMapping
    public TicketResponse createTicket(
            @RequestBody TicketRequest request
    ) {

        /*
         * Step 1:
         * Create the ticket.
         */
        Ticket ticket =
                ticketService.createTicket(request);

        /*
         * Step 2:
         * Immediately send the ticket through
         * RAG + AI analysis.
         */
        ticketAnalysisService.analyzeTicket(
                ticket.getId()
        );

        /*
         * Step 3:
         * Fetch the updated ticket.
         */
        Ticket updatedTicket =
                ticketService.getTicket(
                        ticket.getId()
                );

        return ticketService.toResponse(
                updatedTicket
        );
    }

    @GetMapping
    public List<Ticket> getAllTickets() {

        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketResponse getTicket(
            @PathVariable String id
    ) {

        Ticket ticket =
                ticketService.getTicket(id);

        if (ticket == null) {
            throw new IllegalArgumentException(
                    "Ticket not found: " + id
            );
        }

        return ticketService.toResponse(ticket);
    }

    /*
     * Optional:
     * Allows an existing OPEN ticket to be
     * analyzed manually.
     */
    @PostMapping("/{id}/analyze")
    public TicketResponse analyzeTicket(
            @PathVariable String id
    ) {

        ticketAnalysisService.analyzeTicket(id);

        Ticket ticket =
                ticketService.getTicket(id);

        return ticketService.toResponse(ticket);
    }
}