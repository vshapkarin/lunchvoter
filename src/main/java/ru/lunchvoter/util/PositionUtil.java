package ru.lunchvoter.util;

import ru.lunchvoter.model.Position;
import ru.lunchvoter.model.Restaurant;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class PositionUtil {

    public static Iterable<Position> getPositions(Restaurant restaurant, LocalDate date, Map<String, Integer> menu) {
        return menu.entrySet().stream()
                .map(pos -> new Position(null, date, pos.getKey(), pos.getValue(), restaurant))
                .collect(Collectors.toList());
    }
}
