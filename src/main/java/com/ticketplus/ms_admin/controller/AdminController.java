package com.ticketplus.ms_admin.controller;

import com.ticketplus.ms_admin.dto.QueueRequest;
import com.ticketplus.ms_admin.service.RabbitAdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final RabbitAdminService rabbitAdminService;

    public AdminController(RabbitAdminService rabbitAdminService) {
        this.rabbitAdminService = rabbitAdminService;
    }

    @PostMapping("/queues")
    public ResponseEntity<String> crearCola(@Valid @RequestBody QueueRequest request) {
        rabbitAdminService.crearCola(request.getNombre());
        return ResponseEntity.ok("Cola creada exitosamente en RabbitMQ: " + request.getNombre());
    }

    @DeleteMapping("/queues/{name}")
    public ResponseEntity<String> eliminarCola(@PathVariable String name) {
        rabbitAdminService.eliminarCola(name);
        return ResponseEntity.ok("Cola eliminada de RabbitMQ: " + name);
    }
}