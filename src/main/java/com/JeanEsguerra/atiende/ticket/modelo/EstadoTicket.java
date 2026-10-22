package com.JeanEsguerra.atiende.ticket.modelo;

public enum EstadoTicket {
    ABIERTO,
    EN_PROCESO,
    RESUELTO,
    CERRADO;

    /**
     * Evalúa si es válida la transición hacia el estado destino según las reglas de negocio de la SPEC.
     * CERRADO es un estado final.
     *
     * @param destino Estado al que se desea transicionar
     * @return true si la transición está permitida, false en cualquier otro caso
     */
    public boolean puedeCambiarA(EstadoTicket destino) {
        if (destino == null) {
            return false;
        }

        return switch (this) {
            case ABIERTO -> destino == EN_PROCESO || destino == CERRADO;
            case EN_PROCESO -> destino == RESUELTO;
            case RESUELTO -> destino == CERRADO || destino == EN_PROCESO;
            case CERRADO -> false;
        };
    }
}
