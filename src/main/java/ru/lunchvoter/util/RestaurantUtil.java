package ru.lunchvoter.util;

import org.springframework.util.Assert;
import ru.lunchvoter.model.Position;
import ru.lunchvoter.model.Restaurant;
import ru.lunchvoter.to.RestaurantTo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RestaurantUtil {

    public static RestaurantTo getRestaurantTo(Restaurant restaurant) {
        Set<Position> positions = restaurant.getPositions();
        Assert.notEmpty(positions, "No menu found for restaurant id = " + restaurant.getId());
        return new RestaurantTo(restaurant.getId(),
                restaurant.getName(),
                positions.iterator().next().getDate(),
                positions.stream()
                        .collect(Collectors.toMap(Position::getName, Position::getPrice)));
    }

    public static List<RestaurantTo> getRestaurantTos(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(res -> new RestaurantTo(res.getId(), res.getName()))
                .collect(Collectors.toList());
    }

    public static Restaurant getNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName());
    }

    public static Restaurant getOldFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName());
    }
}
