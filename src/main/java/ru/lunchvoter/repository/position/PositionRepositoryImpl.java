package ru.lunchvoter.repository.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.lunchvoter.model.Position;

import java.time.LocalDate;
import java.util.List;

@Repository
public class PositionRepositoryImpl implements PositionRepository {

    private final PositionJpaRepository jpaRepository;

    @Autowired
    public PositionRepositoryImpl(PositionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Position> save(Iterable<Position> positions) {
        return jpaRepository.saveAll(positions);
    }

    @Override
    public boolean deleteByRestaurantIdAndDate(int restaurantId, LocalDate date) {
        return jpaRepository.deleteByRestaurantIdAndDate(restaurantId, date) != 0;
    }
}
