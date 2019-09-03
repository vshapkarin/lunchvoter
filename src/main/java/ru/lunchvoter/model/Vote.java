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

    @Column(name = "user_id", nullable = false)
    @NotNull
    private int userId;

    @Column(name = "restaurant_id", nullable = false)
    @NotNull
    private int restaurantId;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    public Vote() {
    }

    public Vote(int userId, int restaurantId, LocalDate date) {
        this(null, userId, restaurantId, date);
    }

    public Vote(Long id, int userId, int restaurantId, LocalDate date) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
