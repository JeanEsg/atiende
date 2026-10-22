package com.JeanEsguerra.atiende.ticket.servicio;

import com.JeanEsguerra.atiende.ticket.dto.ActualizarEstadoTicketRequest;
import com.JeanEsguerra.atiende.ticket.dto.CrearTicketRequest;
import com.JeanEsguerra.atiende.ticket.dto.TicketRespuesta;
import com.JeanEsguerra.atiende.ticket.excepcion.TicketNoEncontradoException;
import com.JeanEsguerra.atiende.ticket.excepcion.TransicionEstadoNoPermitidaException;
import com.JeanEsguerra.atiende.ticket.mapeador.TicketMapeador;
import com.JeanEsguerra.atiende.ticket.modelo.CanalTicket;
import com.JeanEsguerra.atiende.ticket.modelo.EstadoTicket;
import com.JeanEsguerra.atiende.ticket.modelo.PrioridadTicket;
import com.JeanEsguerra.atiende.ticket.modelo.Ticket;
import com.JeanEsguerra.atiende.ticket.repositorio.TicketRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServicioTest {

    @Mock
    private TicketRepositorio ticketRepositorio;

    private TicketMapeador ticketMapeador;
    private TicketServicio ticketServicio;

    @BeforeEach
    void setUp() {
        ticketMapeador = new TicketMapeador();
        ticketServicio = new TicketServicio(ticketRepositorio, ticketMapeador);
    }

    @Test
    @DisplayName("crear_solicitudValida_asignaEstadoAbiertoYPrioridadMedia")
    void crear_solicitudValida_asignaEstadoAbiertoYPrioridadMedia() {
        CrearTicketRequest request = new CrearTicketRequest(
                "Laura",
                "laura@correo.com",
                CanalTicket.WHATSAPP,
                "Mensaje de consulta"
        );

        when(ticketRepositorio.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket t = invocation.getArgument(0);
            t.setId(1L);
            t.setFechaCreacion(LocalDateTime.now());
            t.setFechaActualizacion(LocalDateTime.now());
            return t;
        });

        TicketRespuesta respuesta = ticketServicio.crear(request);

        assertNotNull(respuesta);
        assertEquals(EstadoTicket.ABIERTO, respuesta.estado());
        assertEquals(PrioridadTicket.MEDIA, respuesta.prioridad());
        verify(ticketRepositorio).save(any(Ticket.class));
    }

    @Test
    @DisplayName("buscarPorId_idInexistente_lanzaTicketNoEncontradoException")
    void buscarPorId_idInexistente_lanzaTicketNoEncontradoException() {
        Long idInexistente = 99L;
        when(ticketRepositorio.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(
                TicketNoEncontradoException.class,
                () -> ticketServicio.buscarPorId(idInexistente)
        );

        verify(ticketRepositorio).findById(idInexistente);
    }

    @Test
    @DisplayName("cambiarEstado_transicionPermitida_cambiaElEstado")
    void cambiarEstado_transicionPermitida_cambiaElEstado() {
        Long id = 1L;
        Ticket ticketExistente = new Ticket(
                id,
                "Laura",
                "laura@correo.com",
                CanalTicket.WHATSAPP,
                "Mensaje",
                EstadoTicket.ABIERTO,
                PrioridadTicket.MEDIA,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        ActualizarEstadoTicketRequest request = new ActualizarEstadoTicketRequest(EstadoTicket.EN_PROCESO);

        when(ticketRepositorio.findById(id)).thenReturn(Optional.of(ticketExistente));
        when(ticketRepositorio.saveAndFlush(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TicketRespuesta respuesta = ticketServicio.cambiarEstado(id, request);

        assertNotNull(respuesta);
        assertEquals(EstadoTicket.EN_PROCESO, respuesta.estado());
        verify(ticketRepositorio).findById(id);
        verify(ticketRepositorio).saveAndFlush(ticketExistente);
    }

    @Test
    @DisplayName("cambiarEstado_transicionBloqueada_lanzaTransicionEstadoNoPermitidaExceptionYNuncaLlamaSaveAndFlush")
    void cambiarEstado_transicionBloqueada_lanzaTransicionEstadoNoPermitidaExceptionYNuncaLlamaSaveAndFlush() {
        Long id = 1L;
        Ticket ticketExistente = new Ticket(
                id,
                "Laura",
                "laura@correo.com",
                CanalTicket.WHATSAPP,
                "Mensaje",
                EstadoTicket.ABIERTO,
                PrioridadTicket.MEDIA,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        ActualizarEstadoTicketRequest request = new ActualizarEstadoTicketRequest(EstadoTicket.RESUELTO);

        when(ticketRepositorio.findById(id)).thenReturn(Optional.of(ticketExistente));

        assertThrows(
                TransicionEstadoNoPermitidaException.class,
                () -> ticketServicio.cambiarEstado(id, request)
        );

        verify(ticketRepositorio).findById(id);
        verify(ticketRepositorio, never()).saveAndFlush(any(Ticket.class));
    }
}
