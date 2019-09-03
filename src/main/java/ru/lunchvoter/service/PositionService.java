package ru.lunchvoter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoter.model.Restaurant;
import ru.lunchvoter.repository.position.PositionRepository;
import ru.lunchvoter.repository.restaurant.RestaurantRepository;
import ru.lunchvoter.util.PositionUtil;
import ru.lunchvoter.util.ValidationUtil;

import java.time.LocalDate;
import java.util.Map;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public PositionService(PositionRepository positionRepository, RestaurantRepository restaurantRepository) {
        this.positionRepository = positionRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public void update(Restaurant restaurant, LocalDate date, Map<String, Integer> menu) {
        int restaurantId = restaurant.getId();
        restaurantRepository.get(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException(ValidationUtil.getWrongRestaurantMessage(restaurantId)));

        positionRepository.deleteByRestaurantIdAndDate(restaurantId, date);
        positionRepository.save(PositionUtil.getPositions(restaurant, date, menu));
    }
}
