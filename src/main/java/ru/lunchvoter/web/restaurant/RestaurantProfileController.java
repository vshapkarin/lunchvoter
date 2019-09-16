package ru.lunchvoter.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.lunchvoter.AuthorizedUser;
import ru.lunchvoter.repository.restaurant.RestaurantRepositoryImpl;
import ru.lunchvoter.service.VoteService;
import ru.lunchvoter.to.RestaurantTo;
import ru.lunchvoter.to.VoteTo;
import ru.lunchvoter.util.RestaurantUtil;
import ru.lunchvoter.util.TimeUtil;
import ru.lunchvoter.util.exception.NotFoundException;

import java.util.List;

import static ru.lunchvoter.util.TimeUtil.getMenuDate;
import static ru.lunchvoter.web.restaurant.RestaurantProfileController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class RestaurantProfileController {

    public static final String REST_URL = "/restaurants";

    private Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantRepositoryImpl restaurantRepository;

    private final VoteService voteService;

    @Autowired
    public RestaurantProfileController(RestaurantRepositoryImpl restaurantRepository, VoteService voteService) {
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
        return RestaurantUtil.getTo(restaurantRepository.getWithPositionsByDate(id, getMenuDate())
                .orElseThrow(() -> new NotFoundException("Not found restaurant with id = " + id)), getMenuDate());
    }

    @PostMapping("/{id}")
    public ResponseEntity<VoteTo> vote(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("{} votes for restaurant with id = {}", authUser, id);
        VoteTo voteTo = voteService.vote(authUser.getUser(), id, getMenuDate(), TimeUtil.CHANGE_MIND_TIME);
        if(voteTo.isOld()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(voteTo);
        }
        return ResponseEntity.ok(voteTo);
    }
}
