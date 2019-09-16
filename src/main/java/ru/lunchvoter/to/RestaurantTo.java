package ru.lunchvoter.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class RestaurantTo extends AbstractTo<Integer> {

    @NotBlank(groups = RestaurantValidation.class)
    @Size(min = 1, max = 50, groups = RestaurantValidation.class)
    private String name;

    @NotNull(groups = MenuValidation.class)
    private LocalDate menuDate;

    @NotEmpty(groups = MenuValidation.class)
    private Map<String, Integer> menu;

    public RestaurantTo() {
    }

    public RestaurantTo(Integer id, String name) {
        this(id, name, null, null);
    }

    public RestaurantTo(Integer id, String name, LocalDate menuDate, Map<String, Integer> menu) {
        this.id = id;
        this.name = name;
        this.menuDate = menuDate;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }

    public Map<String, Integer> getMenu() {
        return menu;
    }

    public void setMenu(Map<String, Integer> menu) {
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo that = (RestaurantTo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(menuDate, that.menuDate) &&
                Objects.equals(menu, that.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, menuDate, menu);
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "name='" + name + '\'' +
                ", menuDate=" + menuDate +
                ", menu=" + menu +
                ", id=" + id +
                '}';
    }

    public interface RestaurantValidation {
    }

    public interface MenuValidation {
    }
}
