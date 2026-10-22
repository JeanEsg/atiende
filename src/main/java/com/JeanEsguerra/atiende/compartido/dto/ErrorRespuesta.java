package com.JeanEsguerra.atiende.compartido.dto;

import java.time.LocalDateTime;

public record ErrorRespuesta(
        int codigo,
        String error,
        String mensaje,
        LocalDateTime fecha
) {
}
