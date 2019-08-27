package ru.lunchvoter.repository.position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoter.model.Position;

import java.time.LocalDate;

public interface PositionJpaRepository extends JpaRepository<Position, Long> {

    @Transactional
    @Modifying
    void deleteByRestaurantIdAndDate(int restaurantId, LocalDate date);
}
