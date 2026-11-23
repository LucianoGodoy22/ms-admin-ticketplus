package com.ticketplus.ms_admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record TopicRequest(
        @NotBlank(message = "El nombre del tópico es obligatorio")
        String nombre,

        @Min(value = 1, message = "Debe tener al menos 1 partición")
        int particiones,

        @Min(value = 1, message = "Debe tener al menos 1 réplica")
        short replicas
) {}