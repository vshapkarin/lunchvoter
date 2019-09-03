package ru.lunchvoter.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lunchvoter.AbstractServiceAndRepositoryTest;
import ru.lunchvoter.data.RestaurantTestData;
import ru.lunchvoter.model.Position;
import ru.lunchvoter.model.Restaurant;
import ru.lunchvoter.repository.restaurant.RestaurantRepository;

import static ru.lunchvoter.data.PositionTestData.*;

class PositionServiceTest extends AbstractServiceAndRepositoryTest {

    private final PositionService positionService;

    private final RestaurantRepository restaurantRepository;

    @Autowired
    PositionServiceTest(PositionService positionService, RestaurantRepository restaurantRepository) {
        this.positionService = positionService;
        this.restaurantRepository = restaurantRepository;
    }

    @Test
    void update() {
        positionService.update(RestaurantTestData.RESTAURANT1, DATE, getMenu());
        Iterable<Position> actual = restaurantRepository.getWithPositionsByDate(RestaurantTestData.RESTAURANT_ID, DATE)
                .orElse(new Restaurant()).getPositions();
        assertMatch(actual, UPDATED_POSITION);
    }

    @Test
    void updateWrongRestaurant() {
        validateRootCause(
                () ->  positionService.update(new Restaurant(0, "Dummy"), DATE, getMenu()),
                IllegalArgumentException.class);
    }
}
