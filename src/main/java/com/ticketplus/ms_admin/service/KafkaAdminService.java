package com.ticketplus.ms_admin.service;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KafkaAdminService {

    private final KafkaAdmin kafkaAdmin;

    public KafkaAdminService(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    public List<?> listarTopicos() throws Exception {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            Set<String> nombres = adminClient.listTopics().names().get().stream()
                    .filter(nombre -> !nombre.startsWith("__"))
                    .collect(Collectors.toSet());

            if (nombres.isEmpty()) {
                return List.of();
            }

            Map<String, TopicDescription> descripciones = adminClient.describeTopics(nombres).allTopicNames().get();

            return descripciones.values().stream()
                    .map(desc -> Map.<String, Object>of(
                            "nombre", desc.name(),
                            "particiones", desc.partitions().size(),
                            "replicas", desc.partitions().isEmpty() ? 0 : desc.partitions().get(0).replicas().size()
                    ))
                    .toList();
        }
    }

    public void crearTopico(String nombre, int particiones, short replicas) throws Exception {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            NewTopic nuevoTopico = new NewTopic(nombre, particiones, replicas);
            adminClient.createTopics(Collections.singletonList(nuevoTopico)).all().get();
        }
    }

    public void eliminarTopico(String nombre) throws Exception {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            adminClient.deleteTopics(Collections.singletonList(nombre)).all().get();
        }
    }
}