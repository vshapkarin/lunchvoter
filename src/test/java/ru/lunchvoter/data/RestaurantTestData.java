package ru.lunchvoter.data;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.lunchvoter.model.Restaurant;
import ru.lunchvoter.to.RestaurantTo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lunchvoter.TestUtil.readFromJsonMvcResult;
import static ru.lunchvoter.TestUtil.readListFromJsonMvcResult;

public class RestaurantTestData {

    public static final int RESTAURANT_ID = Restaurant.START_SEQ;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT_ID, "DoubleB");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT_ID + 1, "floo");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT_ID + 2, "Cezve");
    public static final Restaurant RESTAURANT4 = new Restaurant(RESTAURANT_ID + 3, "Surf Coffee");

    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT1, RESTAURANT2, RESTAURANT3, RESTAURANT4);

    public static Restaurant getCreated() {
        return new Restaurant(null, "Created restaurant");
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "positions", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("positions", "votes").isEqualTo(expected);
    }

    public static void assertMatchTo(RestaurantTo actual, RestaurantTo expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatchTos(Iterable<RestaurantTo> actual, Iterable<RestaurantTo> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menuDate", "menu").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(List<RestaurantTo> expected) {
        return result -> assertMatchTos(readListFromJsonMvcResult(result, RestaurantTo.class), expected);
    }

    public static ResultMatcher contentJson(RestaurantTo expected) {
        return result -> assertMatchTo(readFromJsonMvcResult(result, RestaurantTo.class), expected);
    }
}
