package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserResponse;
import org.example.services.IUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/users/me",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<UserResponse> usersGet(
    ) {
        return ResponseEntity.ok(userService.getCurrentUser());
    }


}
