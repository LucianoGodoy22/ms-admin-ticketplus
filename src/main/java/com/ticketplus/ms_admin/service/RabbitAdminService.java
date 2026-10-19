package com.ticketplus.ms_admin.service;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;

@Service
public class RabbitAdminService {

    private final AmqpAdmin amqpAdmin;

    public RabbitAdminService(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    public void crearCola(String nombreCola) {
        // Crea una cola duradera (true) que sobrevive a reinicios del servidor
        Queue queue = new Queue(nombreCola, true);
        amqpAdmin.declareQueue(queue);
    }

    public void eliminarCola(String nombreCola) {
        amqpAdmin.deleteQueue(nombreCola);
    }
}