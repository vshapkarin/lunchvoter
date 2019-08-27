package ru.lunchvoter.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lunchvoter.model.Vote;
import ru.lunchvoter.repository.restaurant.RestaurantRepositoryImpl;
import ru.lunchvoter.service.VoteService;
import ru.lunchvoter.to.RestaurantTo;
import ru.lunchvoter.util.RestaurantUtil;
import ru.lunchvoter.util.VoteWrapper;

import java.time.LocalDate;
import java.util.List;

import static ru.lunchvoter.web.SecurityUtil.authUserId;
import static ru.lunchvoter.web.restaurant.RestaurantProfileController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class RestaurantProfileController {

    public static final String REST_URL = "/restaurants";

    private Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantRepositoryImpl repository;

    private final VoteService service;

    @Autowired
    public RestaurantProfileController(RestaurantRepositoryImpl repository, VoteService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("getAll");
        return RestaurantUtil.getRestaurantTos(repository.getAll());
    }

    @GetMapping("/{id}")
    public RestaurantTo getWithPositions(@PathVariable int id) {
        return RestaurantUtil.getRestaurantTo(repository.getWithPositionsByDate(id, LocalDate.now())
                .orElseThrow(() -> new IllegalArgumentException("Not found restaurant with id = " + id)));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Vote> vote(@PathVariable int id) {
        VoteWrapper vote = service.vote(authUserId(), id);
        if(vote.isOld()) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).allow(HttpMethod.GET).body(vote.getVote());
        }
        return ResponseEntity.ok(vote.getVote());
    }
}
