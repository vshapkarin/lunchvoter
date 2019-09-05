package ru.lunchvoter.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@SequenceGenerator(name = "id_generator", sequenceName = "votes_seq", allocationSize = 1, initialValue = Vote.START_SEQ)
@Table(name = "votes", indexes = {
        @Index(columnList = "user_id, date", name = "votes_unique_user_date_idx", unique = true)
})
public class Vote extends AbstractEntity<Long> {
    public static final int START_SEQ = 1_000_000;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    public Vote() {
    }

    public Vote(User user, Restaurant restaurant, LocalDate date) {
        this(null, user, restaurant, date);
    }

    public Vote(Long id, User user, Restaurant restaurant, LocalDate date) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
