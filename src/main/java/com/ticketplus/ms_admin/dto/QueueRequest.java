package com.ticketplus.ms_admin.dto;

import jakarta.validation.constraints.NotBlank;

public class QueueRequest {
    
    @NotBlank(message = "El nombre de la cola no puede estar vacío ni contener solo espacios")
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}