package com.JeanEsguerra.atiende.ticket.servicio;

import com.JeanEsguerra.atiende.ticket.dto.ActualizarEstadoTicketRequest;
import com.JeanEsguerra.atiende.ticket.dto.CrearTicketRequest;
import com.JeanEsguerra.atiende.ticket.dto.TicketRespuesta;
import com.JeanEsguerra.atiende.ticket.excepcion.TicketNoEncontradoException;
import com.JeanEsguerra.atiende.ticket.excepcion.TransicionEstadoNoPermitidaException;
import com.JeanEsguerra.atiende.ticket.mapeador.TicketMapeador;
import com.JeanEsguerra.atiende.ticket.modelo.EstadoTicket;
import com.JeanEsguerra.atiende.ticket.modelo.Ticket;
import com.JeanEsguerra.atiende.ticket.repositorio.TicketRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketServicio {

    private final TicketRepositorio ticketRepositorio;
    private final TicketMapeador ticketMapeador;

    public TicketServicio(TicketRepositorio ticketRepositorio, TicketMapeador ticketMapeador) {
        this.ticketRepositorio = ticketRepositorio;
        this.ticketMapeador = ticketMapeador;
    }

    @Transactional
    public TicketRespuesta crear(CrearTicketRequest request) {
        Ticket ticket = ticketMapeador.aEntidad(request);
        Ticket ticketGuardado = ticketRepositorio.save(ticket);
        return ticketMapeador.aDto(ticketGuardado);
    }

    @Transactional(readOnly = true)
    public List<TicketRespuesta> listar(EstadoTicket estado) {
        List<Ticket> tickets;
        if (estado != null) {
            tickets = ticketRepositorio.findByEstadoOrderByFechaCreacionDesc(estado);
        } else {
            tickets = ticketRepositorio.findAllByOrderByFechaCreacionDesc();
        }
        return tickets.stream()
                .map(ticketMapeador::aDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TicketRespuesta buscarPorId(Long id) {
        Ticket ticket = ticketRepositorio.findById(id)
                .orElseThrow(() -> new TicketNoEncontradoException(id));
        return ticketMapeador.aDto(ticket);
    }

    @Transactional
    public TicketRespuesta cambiarEstado(Long id, ActualizarEstadoTicketRequest request) {
        Ticket ticket = ticketRepositorio.findById(id)
                .orElseThrow(() -> new TicketNoEncontradoException(id));

        if (!ticket.getEstado().puedeCambiarA(request.estado())) {
            throw new TransicionEstadoNoPermitidaException(ticket.getEstado(), request.estado());
        }

        ticket.setEstado(request.estado());
        Ticket ticketActualizado = ticketRepositorio.saveAndFlush(ticket);
        return ticketMapeador.aDto(ticketActualizado);
    }
}
