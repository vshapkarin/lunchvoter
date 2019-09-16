package ru.lunchvoter.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lunchvoter.model.Restaurant;
import ru.lunchvoter.repository.restaurant.RestaurantRepositoryImpl;
import ru.lunchvoter.service.PositionService;
import ru.lunchvoter.to.RestaurantTo;
import ru.lunchvoter.util.RestaurantUtil;
import ru.lunchvoter.util.ValidationUtil;
import ru.lunchvoter.util.exception.NotFoundException;

import java.net.URI;
import java.time.LocalDate;

import static ru.lunchvoter.web.restaurant.RestaurantAdminController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class RestaurantAdminController {

    public static final String REST_URL = "/admin/restaurants";

    private Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantRepositoryImpl restaurantRepository;

    private final PositionService positionService;

    @Autowired
    public RestaurantAdminController(RestaurantRepositoryImpl restaurantRepository,
                                     PositionService positionService) {
        this.restaurantRepository = restaurantRepository;
        this.positionService = positionService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getByDate(@PathVariable int id,
                                @RequestParam LocalDate date) {
        log.info("get restaurant with id = {} and menu date = {}", id, date);
        return restaurantRepository.getWithPositionsByDate(id, date)
                .orElseThrow(() -> new NotFoundException("Restaurant with id = " + id + " doesn't exist"));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(
            @Validated(RestaurantTo.RestaurantValidation.class) @RequestBody RestaurantTo restaurantTo) {
        log.info("create new {}", restaurantTo);
        ValidationUtil.checkNew(restaurantTo);
        Restaurant created = restaurantRepository.save(RestaurantUtil.getNewFromTo(restaurantTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestaurantProfileController.REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant with id = {}", id);
        if (restaurantRepository.delete(id) == 0) {
            throw new NotFoundException("Restaurant with id = " + id + " doesn't exist");
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(RestaurantTo.RestaurantValidation.class) @RequestBody RestaurantTo restaurantTo,
                       @PathVariable int id) {
        log.info("update {} with id = {}", restaurantTo, id);
        ValidationUtil.assureIdConsistent(restaurantTo, id);
        restaurantRepository.save(RestaurantUtil.getFromTo(restaurantTo));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenu(@Validated(RestaurantTo.MenuValidation.class) @RequestBody RestaurantTo restaurantTo,
                           @PathVariable int id) {
        log.info("update menu in {} with id = {}", restaurantTo, id);
        ValidationUtil.assureIdConsistent(restaurantTo, id);
        positionService.update(RestaurantUtil.getFromTo(restaurantTo), restaurantTo.getMenuDate(), restaurantTo.getMenu());
    }
}
