package com.JeanEsguerra.atiende.ticket.dto;

import com.JeanEsguerra.atiende.ticket.modelo.EstadoTicket;
import jakarta.validation.constraints.NotNull;

public record ActualizarEstadoTicketRequest(
        @NotNull(message = "El nuevo estado es obligatorio")
        EstadoTicket estado
) {
}
