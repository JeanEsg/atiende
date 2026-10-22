package com.JeanEsguerra.atiende.ticket.excepcion;

import com.JeanEsguerra.atiende.ticket.modelo.EstadoTicket;

public class TransicionEstadoNoPermitidaException extends RuntimeException {

    public TransicionEstadoNoPermitidaException(EstadoTicket desde, EstadoTicket hacia) {
        super("No se permite la transición de " + desde + " a " + hacia);
    }

    public TransicionEstadoNoPermitidaException(String mensaje) {
        super(mensaje);
    }
}
