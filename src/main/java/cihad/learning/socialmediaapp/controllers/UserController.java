package cihad.learning.socialmediaapp.controllers;

import cihad.learning.socialmediaapp.entities.User;
import cihad.learning.socialmediaapp.services.UserService;
import cihad.learning.socialmediaapp.services.responses.UserResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping("/{userId}")
    public UserResponse get(@PathVariable Long userId) {
        //custom exception
        return new UserResponse(userService.getById(userId));
    }

    @PutMapping("/{userId}")
    public User update(@PathVariable Long userId, @RequestBody User newUser) {
        return userService.update(userId, newUser);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getUserActivity(@PathVariable Long userId) {
        return userService.getUserActivity(userId);
    }
}
