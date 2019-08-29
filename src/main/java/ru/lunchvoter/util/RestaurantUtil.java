package ru.lunchvoter.util;

import ru.lunchvoter.model.Position;
import ru.lunchvoter.model.Restaurant;
import ru.lunchvoter.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {

    public static RestaurantTo getTo(Restaurant restaurant, LocalDate menuDate) {
        return new RestaurantTo(restaurant.getId(),
                restaurant.getName(),
                menuDate,
                restaurant.getPositions().stream()
                        .collect(Collectors.toMap(Position::getName, Position::getPrice)));
    }

    public static List<RestaurantTo> getTos(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(res -> new RestaurantTo(res.getId(), res.getName()))
                .collect(Collectors.toList());
    }

    public static Restaurant getNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName());
    }

    public static Restaurant getFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName());
    }
}
