package ru.lunchvoter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoter.model.Restaurant;
import ru.lunchvoter.repository.position.PositionRepositoryImpl;
import ru.lunchvoter.util.PositionUtil;

import java.time.LocalDate;
import java.util.Map;

@Service
public class PositionService {

    private final PositionRepositoryImpl repository;

    @Autowired
    public PositionService(PositionRepositoryImpl repository) {
        this.repository = repository;
    }

    @Transactional
    public void update(Restaurant restaurant, LocalDate date, Map<String, Integer> menu) {
        //noinspection ConstantConditions
        repository.deleteByRestaurantIdAndDate(restaurant.getId(), date);
        repository.save(PositionUtil.getPositions(restaurant, date, menu));
    }
}
