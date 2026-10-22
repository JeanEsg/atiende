package com.JeanEsguerra.atiende.ticket.excepcion;

public class TicketNoEncontradoException extends RuntimeException {

    public TicketNoEncontradoException(Long id) {
        super("Ticket con id " + id + " no encontrado");
    }

    public TicketNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
