package org.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.entities.EndpointEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IEndpointRepositoryTest {

    @Autowired
    private IEndpointRepository endpointRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @BeforeEach
    void setUp() {
        // Clear test data
        entityManager.createQuery("DELETE FROM MonitoringResultEntity ").executeUpdate();
        entityManager.createQuery("DELETE FROM EndpointEntity").executeUpdate();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void returnCorrectNumberOfCheckableEndpoints() {
        LocalDateTime timestamp1 = LocalDateTime.of(
                LocalDate.of(2024, 1, 1),
                LocalTime.of(12, 0, 0)
        );


        // Insert test data
        EndpointEntity endpoint1 = new EndpointEntity();
        endpoint1.setLastCheckedAt(timestamp1);
        endpoint1.setCheckInterval(60);
        endpoint1.setName("Test Endpoint 1");
        endpoint1.setUrl("http://localhost:8080");
        entityManager.persist(endpoint1);


        // Validate first endpoint
        LocalDateTime checkTimestamp = null;
        List<EndpointEntity> endpoints = null;

        // No endpoint should be returned
        checkTimestamp = timestamp1;
        endpoints = endpointRepository.findCheckableEndpoints(checkTimestamp);

        assertEquals(0, endpoints.size());

        checkTimestamp = timestamp1.minusSeconds(10);
        endpoints = endpointRepository.findCheckableEndpoints(checkTimestamp);

        assertEquals(0, endpoints.size());

        // Endpoint should be returned
        checkTimestamp = timestamp1.plusSeconds(endpoint1.getCheckInterval());
        endpoints = endpointRepository.findCheckableEndpoints(checkTimestamp);

        assertEquals(1, endpoints.size());

        checkTimestamp = timestamp1.plusSeconds(endpoint1.getCheckInterval() + 10);
        endpoints = endpointRepository.findCheckableEndpoints(checkTimestamp);

        assertEquals(1, endpoints.size());


        // Add second endpoint
        LocalDateTime timestamp2 = timestamp1.plusDays(5);

        EndpointEntity endpoint2 = new EndpointEntity();
        endpoint2.setLastCheckedAt(timestamp2);
        endpoint2.setCheckInterval(10);
        endpoint2.setName("Test Endpoint 2");
        endpoint2.setUrl("http://localhost:8081");
        entityManager.persist(endpoint2);

        // No endpoints should be returned
        checkTimestamp = timestamp1;
        endpoints = endpointRepository.findCheckableEndpoints(checkTimestamp);

        assertEquals(0, endpoints.size());

        // Only first endpoint should be returned
        checkTimestamp = timestamp1.plusDays(1);
        endpoints = endpointRepository.findCheckableEndpoints(checkTimestamp);

        assertEquals(1, endpoints.size());

        // Both endpoints should be returned
        checkTimestamp = timestamp2.plusSeconds(endpoint2.getCheckInterval());
        endpoints = endpointRepository.findCheckableEndpoints(checkTimestamp);

        assertEquals(2, endpoints.size());
    }
}