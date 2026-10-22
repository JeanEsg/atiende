package com.JeanEsguerra.atiende.ticket.mapeador;

import com.JeanEsguerra.atiende.ticket.dto.CrearTicketRequest;
import com.JeanEsguerra.atiende.ticket.dto.TicketRespuesta;
import com.JeanEsguerra.atiende.ticket.modelo.EstadoTicket;
import com.JeanEsguerra.atiende.ticket.modelo.PrioridadTicket;
import com.JeanEsguerra.atiende.ticket.modelo.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapeador {

    public Ticket aEntidad(CrearTicketRequest request) {
        if (request == null) {
            return null;
        }

        Ticket ticket = new Ticket();
        ticket.setClienteNombre(request.clienteNombre());
        ticket.setClienteContacto(request.clienteContacto());
        ticket.setCanal(request.canal());
        ticket.setMensaje(request.mensaje());
        // Regla 1 de la SPEC: todo ticket nuevo nace ABIERTO y con prioridad MEDIA
        ticket.setEstado(EstadoTicket.ABIERTO);
        ticket.setPrioridad(PrioridadTicket.MEDIA);

        return ticket;
    }

    public TicketRespuesta aDto(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        return new TicketRespuesta(
                ticket.getId(),
                ticket.getClienteNombre(),
                ticket.getClienteContacto(),
                ticket.getCanal(),
                ticket.getMensaje(),
                ticket.getEstado(),
                ticket.getPrioridad(),
                ticket.getFechaCreacion(),
                ticket.getFechaActualizacion()
        );
    }
}
