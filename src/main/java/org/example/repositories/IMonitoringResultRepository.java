package org.example.repositories;

import org.example.entities.MonitoringResultEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IMonitoringResultRepository extends JpaRepository<MonitoringResultEntity, Long>  {

    @Query("SELECT m " +
            "FROM MonitoringResultEntity  m " +
            "WHERE m.endpoint.id = :endpointId " +
            "AND m.endpoint.user.id = :userId " +
            "ORDER BY m.checkedAt DESC ")
    List<MonitoringResultEntity> getMostRecentResultsForUserAndEndpoint(
            @Param("userId") long userId,
            @Param("endpointId") long endpointId,
            Pageable pageable
    );

    @Modifying
    @Query(value = "DELETE FROM monitoring_result m " +
            "WHERE id IN (SELECT id " +
            "             FROM (SELECT id " +
            "                   FROM monitoring_result " +
            "                   WHERE endpoint_id = :endpointId " +
            "                   ORDER BY checked_at DESC " +
            "                   LIMIT 1000 OFFSET 10) AS outdated) "
    , nativeQuery = true)
    void deleteOutdatedResultsForEndpointId(@Param("endpointId") long endpointId);
}
