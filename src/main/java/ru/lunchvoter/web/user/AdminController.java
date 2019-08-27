package ru.lunchvoter.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lunchvoter.model.User;
import ru.lunchvoter.repository.user.UserRepositoryImpl;
import ru.lunchvoter.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(AdminController.REST_URL)
public class AdminController {

    public static final String REST_URL = "/admin/users";

    private final UserRepositoryImpl repository;

    @Autowired
    public AdminController(UserRepositoryImpl repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<User> getAll() {
        return repository.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable int id) {
        return repository.get(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found user with id = " + id));
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getByEmail(@RequestParam String email) {
        return repository.getByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Not found user with email = " + email));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        ValidationUtil.checkNew(user);
        User created = repository.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        Assert.isTrue(repository.delete(id), "User with id = " + id + " doesn't exist");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @PathVariable int id) {
        ValidationUtil.assureIdConsistent(user, id);
        repository.save(user);
    }
}
