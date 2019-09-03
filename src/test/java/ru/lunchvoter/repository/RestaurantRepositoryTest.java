package ru.lunchvoter.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lunchvoter.AbstractServiceAndRepositoryTest;
import ru.lunchvoter.data.PositionTestData;
import ru.lunchvoter.data.RestaurantTestData;
import ru.lunchvoter.model.Restaurant;
import ru.lunchvoter.repository.restaurant.RestaurantRepository;

import static ru.lunchvoter.data.RestaurantTestData.*;

class RestaurantRepositoryTest extends AbstractServiceAndRepositoryTest {

    private final RestaurantRepository repository;

    @Autowired
    RestaurantRepositoryTest(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Test
    void save() {
        Restaurant newRestaurant = RestaurantTestData.getCreated();
        Restaurant saved = repository.save(newRestaurant);
        assertMatch(saved, newRestaurant);
        assertMatch(repository.getAll(), RESTAURANT1, RESTAURANT2, RESTAURANT3, RESTAURANT4, newRestaurant);
    }

    @Test
    void delete() {
        Assertions.assertThat(repository.delete(RESTAURANT_ID)).isEqualTo(true);
        assertMatch(repository.getAll(), RESTAURANT2, RESTAURANT3, RESTAURANT4);
    }

    @Test
    void get() {
        Restaurant actual = repository.get(RESTAURANT_ID).orElse(null);
        assertMatch(actual, RESTAURANT1);
    }

    @Test
    void getWithPositionsByDate() {
        Restaurant actual = repository.getWithPositionsByDate(RESTAURANT_ID, PositionTestData.DATE).orElse(new Restaurant());
        assertMatch(actual, RESTAURANT1);
        PositionTestData.assertMatch(actual.getPositions(),
                PositionTestData.POSITION3,
                PositionTestData.POSITION2,
                PositionTestData.POSITION1);
    }

    @Test
    void getAll() {
        assertMatch(repository.getAll(), RESTAURANTS);
    }

    @Test
    void deleteNotFound() {
        Assertions.assertThat(repository.delete(RESTAURANT_ID + 10)).isEqualTo(false);
        assertMatch(repository.getAll(), RESTAURANTS);
    }

    @Test
    void getNotFound() {
        Restaurant actual = repository.get(RESTAURANT_ID + 10).orElse(null);
        Assertions.assertThat(actual).isNull();
    }
}
