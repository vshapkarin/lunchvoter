package ru.lunchvoter.model;

import org.hibernate.annotations.Filter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@SuppressWarnings("JpaQlInspection")
@NamedQuery(name = Restaurant.GET_WITH_POSITIONS, query =
        "SELECT r FROM Restaurant r " +
        "LEFT JOIN FETCH r.positions p " +
        "WHERE r.id= :id")
@Entity
@SequenceGenerator(name = "id_generator", sequenceName = "restaurants_seq", allocationSize = 1, initialValue = Restaurant.START_SEQ)
@Table(name = "restaurants", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "restaurant_name_idx")
})
public class Restaurant extends AbstractEntity<Integer> {
    public static final int START_SEQ = 100_000;
    public static final String GET_WITH_POSITIONS = "Restaurant.getWithPositionsByDate";

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Filter(name = "filterByDate", condition = "date = :date")
    private Set<Position> positions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName());
    }

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
