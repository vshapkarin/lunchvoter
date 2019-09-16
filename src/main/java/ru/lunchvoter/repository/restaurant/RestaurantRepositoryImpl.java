package ru.lunchvoter.repository.restaurant;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoter.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class RestaurantRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    private final RestaurantJpaRepository jpaRepository;

    @Autowired
    public RestaurantRepositoryImpl(RestaurantJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        return jpaRepository.save(restaurant);
    }

    public int delete(int id) {
        return jpaRepository.delete(id);
    }

    public Optional<Restaurant> get(int id) {
        return jpaRepository.findById(id);
    }

    //https://stackoverflow.com/a/27369964/12015598
    @Transactional
    public Optional<Restaurant> getWithPositionsByDate(int id, LocalDate date) {
        entityManager.unwrap(Session.class)
                .enableFilter("filterByDate")
                .setParameter("date", date);
        return entityManager.createNamedQuery(Restaurant.GET_WITH_POSITIONS, Restaurant.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    public List<Restaurant> getAll() {
        return jpaRepository.findAll();
    }
}
