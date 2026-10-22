package com.JeanEsguerra.atiende.ticket.controlador;

import com.JeanEsguerra.atiende.ticket.dto.ActualizarEstadoTicketRequest;
import com.JeanEsguerra.atiende.ticket.dto.CrearTicketRequest;
import com.JeanEsguerra.atiende.ticket.dto.TicketRespuesta;
import com.JeanEsguerra.atiende.ticket.modelo.EstadoTicket;
import com.JeanEsguerra.atiende.ticket.servicio.TicketServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketControlador {

    private final TicketServicio ticketServicio;

    public TicketControlador(TicketServicio ticketServicio) {
        this.ticketServicio = ticketServicio;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketRespuesta crear(@Valid @RequestBody CrearTicketRequest request) {
        return ticketServicio.crear(request);
    }

    @GetMapping
    public List<TicketRespuesta> listar(@RequestParam(required = false) EstadoTicket estado) {
        return ticketServicio.listar(estado);
    }

    @GetMapping("/{id}")
    public TicketRespuesta buscarPorId(@PathVariable Long id) {
        return ticketServicio.buscarPorId(id);
    }

    @PatchMapping("/{id}/estado")
    public TicketRespuesta cambiarEstado(@PathVariable Long id,
                                         @Valid @RequestBody ActualizarEstadoTicketRequest request) {
        return ticketServicio.cambiarEstado(id, request);
    }
}
