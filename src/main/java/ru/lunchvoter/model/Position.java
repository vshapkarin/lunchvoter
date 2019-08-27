package ru.lunchvoter.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@SequenceGenerator(name = "id_generator", sequenceName = "menu_positions_seq", allocationSize = 1, initialValue = Position.START_SEQ)
@Table(name = "menu_positions", indexes = {
        @Index(columnList = "date, restaurant_id", name = "menu_positions_idx")
})
public class Position extends AbstractEntity<Long> {
    static final int START_SEQ = 1_000_000;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String name;

    @Column(name = "price", nullable = false)
    @NotNull
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    public Position() {
    }

    public Position(LocalDate date, String name, Integer price, Restaurant restaurant) {
        this.date = date;
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
