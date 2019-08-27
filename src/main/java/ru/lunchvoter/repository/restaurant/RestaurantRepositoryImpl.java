package ru.lunchvoter.repository.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.lunchvoter.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class RestaurantRepositoryImpl {

    private final RestaurantJpaRepository jpaRepository;

    @Autowired
    public RestaurantRepositoryImpl(RestaurantJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        return jpaRepository.save(restaurant);
    }

    public boolean delete(int id) {
        return jpaRepository.delete(id) != 0;
    }

    public Optional<Restaurant> get(int id) {
        return jpaRepository.findById(id);
    }

    public Optional<Restaurant> getWithPositionsByDate(int id, LocalDate date) {
        return jpaRepository.getWithPositionsByDate(id, date);
    }

    public List<Restaurant> getAll() {
        return jpaRepository.findAll();
    }
}
