package org.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.entities.EndpointEntity;
import org.example.entities.MonitoringResultEntity;
import org.example.entities.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IMonitoringResultRepositoryTest {

    @Autowired
    private IMonitoringResultRepository resultRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private UserEntity user;
    private EndpointEntity endpoint;

    @BeforeEach
    void setUp() {
        // Clear test data
        entityManager.createQuery("DELETE FROM MonitoringResultEntity ").executeUpdate();
        entityManager.createQuery("DELETE FROM EndpointEntity").executeUpdate();
        entityManager.createQuery("DELETE FROM UserEntity ").executeUpdate();

        // Insert test data
        user = new UserEntity();
        user.setEmail("hello.world@example.com");
        user.setUsername("Hello World");
        user.setAccessToken("1234567890");
        entityManager.persist(user);

        endpoint = new EndpointEntity();
        endpoint.setLastCheckedAt(null);
        endpoint.setCheckInterval(60);
        endpoint.setName("Test Endpoint 1");
        endpoint.setUrl("http://localhost:8080");
        endpoint.setUser(user);
        entityManager.persist(endpoint);

        entityManager.flush();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void shouldReturnMostRecentResults() {

        // Check with no results
        int pageCount = 10;

        List<MonitoringResultEntity> results = resultRepository.getMostRecentResultsForUserAndEndpoint(user.getId(), endpoint.getId(), PageRequest.of(0, pageCount));

        assertEquals(0, results.size());

        // Add multiple results
        createResults(20);

        results = resultRepository.getMostRecentResultsForUserAndEndpoint(user.getId(), endpoint.getId(), PageRequest.of(0, pageCount));

        assertEquals(pageCount, results.size());

        // Check correct order, most recent first
        for (int i = 0; i < pageCount - 1; i++) {
            assertTrue(results.get(i).getCheckedAt().isAfter(results.get(i + 1).getCheckedAt()));
        }
    }


    @Test
    void shouldCorrectlyRemoveOutdatedResults() {
        // Should not delete if not outdated

        // Add 1
        createResults(1);
        resultRepository.deleteOutdatedResultsForEndpointId(endpoint.getId());
        List<MonitoringResultEntity> results = resultRepository.findAll();

        assertEquals(1, results.size());
        resultRepository.deleteAll();

        // Add 5
        createResults(5);
        resultRepository.deleteOutdatedResultsForEndpointId(endpoint.getId());

        results = resultRepository.findAll();
        assertEquals(5, results.size());
        resultRepository.deleteAll();

        // Add 10
        createResults(10);
        resultRepository.deleteOutdatedResultsForEndpointId(endpoint.getId());

        results = resultRepository.findAll();
        assertEquals(10, results.size());
        resultRepository.deleteAll();

        // Add 11
        createResults(11);
        resultRepository.deleteOutdatedResultsForEndpointId(endpoint.getId());

        results = resultRepository.findAll();
        assertEquals(10, results.size());
        resultRepository.deleteAll();

        // Add 100
        createResults(100);
        resultRepository.deleteOutdatedResultsForEndpointId(endpoint.getId());

        results = resultRepository.findAll();
        assertEquals(10, results.size());
        resultRepository.deleteAll();
    }

    private void createResults(int count) {
        for (int i = 0; i < count; i++) {
            LocalDateTime timestamp = LocalDateTime.of(2024, 1, 1, 12, 0, 0);

            MonitoringResultEntity monitoringResultEntity1 = new MonitoringResultEntity();
            monitoringResultEntity1.setCheckedAt(timestamp.plusHours(i));
            monitoringResultEntity1.setEndpoint(endpoint);
            entityManager.persist(monitoringResultEntity1);
        }
    }
}