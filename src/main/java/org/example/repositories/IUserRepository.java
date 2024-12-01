package org.example.repositories;

import org.example.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for managing user entities.
 */
public interface IUserRepository extends JpaRepository<UserEntity, Long>  {
    /**
     * Finds a user by access token.
     *
     * @param accessToken The access token.
     * @return The user entity.
     */
    Optional<UserEntity> findByAccessToken(String accessToken);
}
