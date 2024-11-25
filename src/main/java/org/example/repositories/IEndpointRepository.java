package org.example.repositories;

import org.example.entities.EndpointEntity;
import org.example.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IEndpointRepository extends JpaRepository<EndpointEntity, Long>  {
    List<EndpointEntity> findByUserId(long userId);
    boolean existsByIdAndUserId(long endpointId, long userId);

    Optional<EndpointEntity> findByIdAndUserId(long endpointId, long userId);

    @Query(value =
            "SELECT e.* " +
            "FROM endpoint e " +
            "WHERE e.last_checked_at IS NULL " +
            "   OR TIMESTAMPADD(SECOND, e.check_interval, e.last_checked_at) <= :timestamp " +
            "ORDER BY e.last_checked_at",
            nativeQuery = true)
    List<EndpointEntity> findCheckableEndpoints(@Param("timestamp") LocalDateTime timestamp);
}
