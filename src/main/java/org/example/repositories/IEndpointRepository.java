package org.example.repositories;

import org.example.entities.EndpointEntity;
import org.example.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing endpoint entities.
 */
public interface IEndpointRepository extends JpaRepository<EndpointEntity, Long>  {
    /**
     * Finds all endpoints by user id.
     *
     * @param userId The user id.
     * @return List of endpoint entities.
     */
    List<EndpointEntity> findByUserId(long userId);

    /**
     * Checks if an endpoint exists by id and user id.
     *
     * @param endpointId The endpoint id.
     * @param userId     The user id.
     * @return True if the endpoint exists, false otherwise.
     */
    boolean existsByIdAndUserId(long endpointId, long userId);

    /**
     * Finds an endpoint by id and user id.
     *
     * @param endpointId The endpoint id.
     * @param userId     The user id.
     * @return The endpoint entity.
     */
    Optional<EndpointEntity> findByIdAndUserId(long endpointId, long userId);

    /**
     * Finds all endpoint ids.
     *
     * @return List of endpoint ids.
     */
    @Query("SELECT e.id " +
            "FROM EndpointEntity e ")
    List<Long> getAllIds();

    /**
     * Finds all endpoints that are due for a check.
     *
     * @param timestamp The timestamp to compare against.
     * @return List of endpoints that are due for a check.
     */
    @Query(value =
            "SELECT e.* " +
            "FROM endpoint e " +
            "WHERE e.last_checked_at IS NULL " +
            "   OR TIMESTAMPADD(SECOND, e.check_interval, e.last_checked_at) <= :timestamp " +
            "ORDER BY e.last_checked_at",
            nativeQuery = true)
    List<EndpointEntity> findCheckableEndpoints(@Param("timestamp") LocalDateTime timestamp);
}
