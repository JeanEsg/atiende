package com.JeanEsguerra.atiende.ticket.dto;

import com.JeanEsguerra.atiende.ticket.modelo.CanalTicket;
import com.JeanEsguerra.atiende.ticket.modelo.EstadoTicket;
import com.JeanEsguerra.atiende.ticket.modelo.PrioridadTicket;

import java.time.LocalDateTime;

public record TicketRespuesta(
        Long id,
        String clienteNombre,
        String clienteContacto,
        CanalTicket canal,
        String mensaje,
        EstadoTicket estado,
        PrioridadTicket prioridad,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
) {
}
