package com.ticketplus.ms_admin.controller;

import com.ticketplus.ms_admin.dto.QueueRequest;
import com.ticketplus.ms_admin.dto.TopicRequest;
import com.ticketplus.ms_admin.service.KafkaAdminService;
import com.ticketplus.ms_admin.service.RabbitAdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final RabbitAdminService rabbitAdminService;
    private final KafkaAdminService kafkaAdminService;

    public AdminController(RabbitAdminService rabbitAdminService, KafkaAdminService kafkaAdminService) {
        this.rabbitAdminService = rabbitAdminService;
        this.kafkaAdminService = kafkaAdminService;
    }

    // --- RabbitMQ ---

    @PostMapping("/queues")
    public ResponseEntity<Object> crearCola(@Valid @RequestBody QueueRequest request) {
        rabbitAdminService.crearCola(request.getNombre());
        return ResponseEntity.ok(Map.of("mensaje", "Cola creada exitosamente: " + request.getNombre()));
    }

    @DeleteMapping("/queues/{name}")
    public ResponseEntity<Object> eliminarCola(@PathVariable String name) {
        rabbitAdminService.eliminarCola(name);
        return ResponseEntity.ok(Map.of("mensaje", "Cola eliminada: " + name));
    }

    // --- Kafka ---

    @GetMapping("/kafka/topicos")
    public ResponseEntity<Object> listarTopicos() {
        try {
            return ResponseEntity.ok(kafkaAdminService.listarTopicos());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Error al listar tópicos: " + e.getMessage()));
        }
    }

    @PostMapping("/kafka/topicos")
    public ResponseEntity<Object> crearTopico(@Valid @RequestBody TopicRequest request) {
        try {
            kafkaAdminService.crearTopico(request.nombre(), request.particiones(), request.replicas());
            return ResponseEntity.ok(Map.of("mensaje", "Tópico creado exitosamente: " + request.nombre()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al crear tópico: " + e.getMessage()));
        }
    }

    @DeleteMapping("/kafka/topicos/{name}")
    public ResponseEntity<Object> eliminarTopico(@PathVariable String name) {
        try {
            kafkaAdminService.eliminarTopico(name);
            return ResponseEntity.ok(Map.of("mensaje", "Tópico eliminado: " + name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al eliminar tópico: " + e.getMessage()));
        }
    }
}