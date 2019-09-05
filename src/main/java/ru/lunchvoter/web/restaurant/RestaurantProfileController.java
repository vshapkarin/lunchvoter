package ru.lunchvoter.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.lunchvoter.AuthorizedUser;
import ru.lunchvoter.model.Vote;
import ru.lunchvoter.repository.restaurant.RestaurantRepository;
import ru.lunchvoter.service.VoteService;
import ru.lunchvoter.to.RestaurantTo;
import ru.lunchvoter.util.RestaurantUtil;
import ru.lunchvoter.util.TimeUtil;
import ru.lunchvoter.util.VoteWrapper;

import java.time.LocalDate;
import java.util.List;

import static ru.lunchvoter.web.restaurant.RestaurantProfileController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class RestaurantProfileController {

    public static final String REST_URL = "/restaurants";

    private static final LocalDate MENU_DATE = LocalDate.now();

    private Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantRepository restaurantRepository;

    private final VoteService voteService;

    @Autowired
    public RestaurantProfileController(RestaurantRepository restaurantRepository, VoteService voteService) {
        this.restaurantRepository = restaurantRepository;
        this.voteService = voteService;
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("getAll");
        return RestaurantUtil.getTos(restaurantRepository.getAll());
    }

    @GetMapping("/{id}")
    public RestaurantTo getWithPositions(@PathVariable int id) {
        log.info("get restaurant with id = {}", id);
        return RestaurantUtil.getTo(restaurantRepository.getWithPositionsByDate(id, MENU_DATE)
                .orElseThrow(() -> new IllegalArgumentException("Not found restaurant with id = " + id)), MENU_DATE);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Vote> vote(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("{} votes for restaurant with id = {}", authUser, id);
        VoteWrapper vote = voteService.vote(authUser.getUser(), id, MENU_DATE, TimeUtil.CHANGE_MIND_TIME);
        if(vote.isOld()) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).allow(HttpMethod.GET).body(vote.getVote());
        }
        return ResponseEntity.ok(vote.getVote());
    }
}
