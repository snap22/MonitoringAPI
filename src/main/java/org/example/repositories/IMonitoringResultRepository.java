package org.example.repositories;

import org.example.entities.MonitoringResultEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
