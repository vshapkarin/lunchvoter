package ru.lunchvoter.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lunchvoter.AuthorizedUser;
import ru.lunchvoter.model.User;
import ru.lunchvoter.service.UserService;
import ru.lunchvoter.to.UserTo;
import ru.lunchvoter.util.UserUtil;
import ru.lunchvoter.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;

import static ru.lunchvoter.web.user.ProfileController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class ProfileController {

    public static final String REST_URL = "/profile";

    private Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    @Autowired
    public ProfileController(UserService service) {
        this.service = service;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("register new {}", userTo);
        ValidationUtil.checkNew(userTo);
        User created = service.save(UserUtil.getNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserTo get(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get profile {}", authUser);
        return UserUtil.getTo(service.get(authUser.getId()));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("delete profile {}", authUser);
        service.delete(authUser.getId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserTo userTo, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("update {}", userTo);
        ValidationUtil.assureIdConsistent(userTo, authUser.getId());
        service.update(userTo);
    }
}
