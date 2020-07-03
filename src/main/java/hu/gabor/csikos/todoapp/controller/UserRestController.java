package hu.gabor.csikos.todoapp.controller;

import hu.gabor.csikos.todoapp.dto.UserDTO;
import hu.gabor.csikos.todoapp.dto.UserDTOWithPassword;
import hu.gabor.csikos.todoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController

@RequestMapping("user")
public class UserRestController {

    @Autowired
    private UserService service;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        UserDTO response = service.register(userDTO);
        return ResponseEntity.ok(response);

    }


    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
        UserDTO response = service.update(userDTO);
        return ResponseEntity.ok(response);

    }


    @PutMapping("/updatepassword")

    public ResponseEntity<UserDTO> updatePassWord(@RequestBody UserDTOWithPassword userDTO) {
        UserDTO response = service.updatePassword(userDTO);
        return ResponseEntity.ok(response);

    }

}