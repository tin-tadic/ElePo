package com.faks.elepo.controller;

import com.faks.elepo.config.security.ContextReader;
import com.faks.elepo.database.repository.CommentRepository;
import com.faks.elepo.database.repository.UserRepository;
import com.faks.elepo.model.User;
import com.faks.elepo.model.dto.SetUserRoleDTO;
import com.faks.elepo.model.dto.UpdateUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;
    private ContextReader contextReader;
    private CommentRepository commentRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(8);

    public UserController(UserRepository userRepository, ContextReader contextReader, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.contextReader = contextReader;
        this.commentRepository = commentRepository;
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateUserDetails(@PathVariable Long id, @Validated @RequestBody UpdateUserDTO updateUserDTO) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User does not exist!", HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();

        if (!contextReader.getLoggedInUser().getId().equals(user.getId()) && !contextReader.getLoggedInUser().getRole().equals("ROLE_ADMIN")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (!updateUserDTO.getEmail().equals(user.getEmail())) {
            Optional<User> maybeExistingUser = userRepository.findByEmail(updateUserDTO.getEmail());
            if (maybeExistingUser.isPresent()) {
                return new ResponseEntity<>("User with that email already exists!", HttpStatus.BAD_REQUEST);
            }
        }

        if (!updateUserDTO.getUsername().equals(user.getUsername())) {
            Optional<User> maybeExistingUser = userRepository.findByUsername(updateUserDTO.getUsername());
            if (maybeExistingUser.isPresent()) {
                return new ResponseEntity<>("User with that username already exists!", HttpStatus.BAD_REQUEST);
            }
        }

        user.setEmail(updateUserDTO.getEmail());
        user.setUsername(updateUserDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(updateUserDTO.getPassword()));

        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            commentRepository.deleteByUserId(id);
            userRepository.delete(optionalUser.get());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/change-role/{id}")
    private ResponseEntity<String> changeRole(@PathVariable Long id, @Validated @RequestBody SetUserRoleDTO setUserRoleDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();

        user.setRole(setUserRoleDTO.getRole());
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/toggle-disabled/{id}")
    private ResponseEntity<String> toggleDisabled(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();

        if (user.isDisabled()) {
            user.setDisabled(false);
        } else {
            user.setDisabled(true);
        }
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
