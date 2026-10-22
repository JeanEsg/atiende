package com.JeanEsguerra.atiende.ticket.dto;

import com.JeanEsguerra.atiende.ticket.modelo.CanalTicket;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CrearTicketRequest(
        @NotBlank(message = "El nombre del cliente es obligatorio")
        @Size(max = 100, message = "El nombre del cliente no puede superar los 100 caracteres")
        String clienteNombre,

        @NotBlank(message = "El contacto del cliente es obligatorio")
        @Size(max = 120, message = "El contacto del cliente no puede superar los 120 caracteres")
        String clienteContacto,

        @NotNull(message = "El canal es obligatorio")
        CanalTicket canal,

        @NotBlank(message = "El mensaje es obligatorio")
        @Size(max = 2000, message = "El mensaje no puede superar los 2000 caracteres")
        String mensaje
) {
}
