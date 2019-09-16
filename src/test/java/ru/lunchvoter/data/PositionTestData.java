package ru.lunchvoter.data;

import ru.lunchvoter.model.Position;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionTestData {

    public static final LocalDate DATE = LocalDate.now();

    public static final long POSITION_ID = Position.START_SEQ;

    public static final Position POSITION1 = new Position(POSITION_ID, DATE, "Cappuccino", 500, RestaurantTestData.RESTAURANT1);
    public static final Position POSITION2 = new Position(POSITION_ID + 1, DATE, "Hario V60", 300, RestaurantTestData.RESTAURANT1);
    public static final Position POSITION3 = new Position(POSITION_ID + 2, DATE, "Almond croissant", 200, RestaurantTestData.RESTAURANT1);

    public static final Position UPDATED_POSITION = new Position(POSITION_ID + 10, DATE, "TestPosition1", 100, RestaurantTestData.RESTAURANT1);

    public static Map<String, Integer> getMenu() {
        return Map.of("TestPosition1", 100);
    }

    public static void assertMatch(Iterable<Position> actual, Position... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Position> actual, Iterable<Position> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }
}
