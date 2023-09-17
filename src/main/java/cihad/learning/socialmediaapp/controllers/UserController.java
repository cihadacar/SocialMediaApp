package cihad.learning.socialmediaapp.controllers;

import cihad.learning.socialmediaapp.entities.User;
import cihad.learning.socialmediaapp.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public User get(@PathVariable Long userId) {
        //custom exception
        return userService.getById(userId);
    }

    @PutMapping("/{userId}")
    public User update(@PathVariable Long userId, @RequestBody User newUser) {
        return userService.update(userId, newUser);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

}
