package tn.esprit.projetpi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetpi.entities.User;
import tn.esprit.projetpi.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(user);

    }
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
       List<User> users =  userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Optional<User> oldUser = userService.getUserById(user.getId());
        if (oldUser.isPresent()) {
           User updatedUser = oldUser.get();
           updatedUser.setName(user.getName());
           updatedUser.setEmail(user.getEmail());
           userService.saveUser(updatedUser);
           return ResponseEntity.ok(updatedUser);

        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> oldUser = userService.getUserById(id);
        if (oldUser.isPresent()) {
            userService.deleteUser(oldUser.get().getId());
            return ResponseEntity.ok("User Deleted");
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> oldUser = userService.getUserById(id);
        if (oldUser.isPresent()) {
            return ResponseEntity.ok(oldUser.get());
        }
        return ResponseEntity.notFound().build();
    }

}
