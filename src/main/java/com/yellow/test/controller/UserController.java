package com.yellow.test.controller;

import com.yellow.test.model.user.SaveUserDTO;
import com.yellow.test.model.user.UserDTO;
import com.yellow.test.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody SaveUserDTO dto) {
        UserDTO saved = service.save(dto);
        return ResponseEntity.ok(saved);
    }

}
