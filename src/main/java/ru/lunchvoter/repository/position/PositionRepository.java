package ru.lunchvoter.repository.position;

import ru.lunchvoter.model.Position;

import java.time.LocalDate;
import java.util.List;

public interface PositionRepository {

    List<Position> save(Iterable<Position> positions);

    boolean deleteByRestaurantIdAndDate(int restaurantId, LocalDate date);
}
