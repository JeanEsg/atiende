package com.JeanEsguerra.atiende.ticket.repositorio;

import com.JeanEsguerra.atiende.ticket.modelo.EstadoTicket;
import com.JeanEsguerra.atiende.ticket.modelo.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepositorio extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByOrderByFechaCreacionDesc();

    List<Ticket> findByEstadoOrderByFechaCreacionDesc(EstadoTicket estado);
}
