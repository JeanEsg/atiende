package com.JeanEsguerra.atiende.ticket.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EstadoTicketTest {

    @ParameterizedTest(name = "Transición permitida: {0} -> {1}")
    @CsvSource({
            "ABIERTO, EN_PROCESO",
            "ABIERTO, CERRADO",
            "EN_PROCESO, RESUELTO",
            "RESUELTO, CERRADO",
            "RESUELTO, EN_PROCESO"
    })
    @DisplayName("puedeCambiarA_transicionPermitida_retornaTrue")
    void puedeCambiarA_transicionPermitida_retornaTrue(EstadoTicket origen, EstadoTicket destino) {
        assertTrue(origen.puedeCambiarA(destino));
    }

    @ParameterizedTest(name = "Transición bloqueada: {0} -> {1}")
    @CsvSource({
            "ABIERTO, RESUELTO",
            "EN_PROCESO, ABIERTO",
            "EN_PROCESO, CERRADO",
            "RESUELTO, ABIERTO",
            "CERRADO, ABIERTO",
            "CERRADO, EN_PROCESO"
    })
    @DisplayName("puedeCambiarA_transicionBloqueada_retornaFalse")
    void puedeCambiarA_transicionBloqueada_retornaFalse(EstadoTicket origen, EstadoTicket destino) {
        assertFalse(origen.puedeCambiarA(destino));
    }
}
