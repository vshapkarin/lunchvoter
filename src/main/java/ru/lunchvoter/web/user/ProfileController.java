package ru.lunchvoter.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lunchvoter.model.Role;
import ru.lunchvoter.model.User;
import ru.lunchvoter.repository.user.UserRepositoryImpl;
import ru.lunchvoter.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.EnumSet;

import static ru.lunchvoter.web.SecurityUtil.authUserId;
import static ru.lunchvoter.web.user.ProfileController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class ProfileController {

    public static final String REST_URL = "/profile";

    private final UserRepositoryImpl repository;

    @Autowired
    public ProfileController(UserRepositoryImpl repository) {
        this.repository = repository;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        ValidationUtil.checkNew(user);
        user.setRoles(EnumSet.of(Role.ROLE_USER));
        User created = repository.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return repository.get(authUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        Assert.isTrue(repository.delete(authUserId()), "User doesn't exist");
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user) {
        Assert.notNull(user, "User must not be null");
        repository.save(user);
    }
}
