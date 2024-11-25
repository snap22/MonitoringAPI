package org.example.services.implementation;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserResponse;
import org.example.entities.UserEntity;
import org.example.exceptions.ResourceNotFoundException;
import org.example.exceptions.UnauthorizedException;
import org.example.mappers.IUserMapper;
import org.example.repositories.IUserRepository;
import org.example.services.IUserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

    @Override
    public UserEntity findByAccessToken(String accessToken) {
        return userRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new BadCredentialsException("Invalid access token"));
    }

    @Override
    public UserEntity getCurrentUserEntity() {
        Long currentUserId = getCurrentUserId();
        return userRepository.findById(currentUserId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserResponse getCurrentUser() {
        return userMapper.entityToResponse(getCurrentUserEntity());
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException();
        }
        return (Long) authentication.getPrincipal();
    }
}
