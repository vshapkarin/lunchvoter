package ru.lunchvoter.repository.restaurant;

import ru.lunchvoter.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    boolean delete(int id);

    Optional<Restaurant> get(int id);

    Optional<Restaurant> getWithPositionsByDate(int id, LocalDate date);

    List<Restaurant> getAll();
}
