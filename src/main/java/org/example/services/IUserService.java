package org.example.services;

import org.example.dto.UserResponse;
import org.example.entities.UserEntity;
import org.example.exceptions.ResourceNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;

public interface IUserService {
    /**
     * Finds a user by their access token.
     *
     * @param accessToken the access token of the user
     * @return the UserEntity associated with the access token
     * @throws BadCredentialsException if the access token is invalid
     */
    UserEntity findByAccessToken(String accessToken);

    /**
     * Retrieves the current user entity.
     *
     * @return the current UserEntity
     * @throws ResourceNotFoundException if the user is not found
     */
    UserEntity getCurrentUserEntity();

    /**
     * Retrieves the current user DTO.
     *
     * @return the UserResponse representing the current user
     */
    UserResponse getCurrentUser();
}
