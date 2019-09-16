package ru.lunchvoter.repository.position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoter.model.Position;

import java.time.LocalDate;

@Transactional(readOnly = true)
public interface PositionJpaRepository extends JpaRepository<Position, Long> {

    @Transactional
    int deleteByRestaurantIdAndDate(int restaurantId, LocalDate date);
}
