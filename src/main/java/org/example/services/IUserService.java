package org.example.services;

import org.example.dto.UserResponse;
import org.example.entities.UserEntity;

public interface IUserService {
    UserEntity findByAccessToken(String accessToken);

    UserEntity getCurrentUserEntity();

    UserResponse getCurrentUser();
}
