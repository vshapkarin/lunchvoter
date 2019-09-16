package ru.lunchvoter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoter.model.Restaurant;
import ru.lunchvoter.repository.position.PositionJpaRepository;
import ru.lunchvoter.repository.restaurant.RestaurantRepositoryImpl;
import ru.lunchvoter.util.PositionUtil;
import ru.lunchvoter.util.ValidationUtil;
import ru.lunchvoter.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Map;

@Service
public class PositionService {

    private final PositionJpaRepository positionRepository;

    private final RestaurantRepositoryImpl restaurantRepository;

    @Autowired
    public PositionService(PositionJpaRepository positionRepository, RestaurantRepositoryImpl restaurantRepository) {
        this.positionRepository = positionRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public void update(Restaurant restaurant, LocalDate date, Map<String, Integer> menu) {
        int restaurantId = restaurant.getId();
        restaurantRepository.get(restaurantId)
                .orElseThrow(() -> new NotFoundException(ValidationUtil.getWrongRestaurantMessage(restaurantId)));

        positionRepository.deleteByRestaurantIdAndDate(restaurantId, date);
        positionRepository.saveAll(PositionUtil.getPositions(restaurant, date, menu));
    }
}
