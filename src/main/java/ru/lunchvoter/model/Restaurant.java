package ru.lunchvoter.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@SequenceGenerator(name = "id_generator", sequenceName = "restaurants_seq", allocationSize = 1, initialValue = Restaurant.START_SEQ)
@Table(name = "restaurants", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "restaurant_name_idx")
})
public class Restaurant extends AbstractEntity<Integer> {
    static final int START_SEQ = 100_000;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Position> positions;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "votes", joinColumns = @JoinColumn(name = "restaurant_id"))
    private Set<Long> voteIds;

    public Restaurant() {
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

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
