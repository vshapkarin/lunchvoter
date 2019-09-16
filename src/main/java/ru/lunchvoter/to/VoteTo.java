package ru.lunchvoter.to;

public class VoteTo extends AbstractTo<Long> {

    private String restaurantName;

    private boolean isOld;

    public VoteTo() {
    }

    public VoteTo(Long id, String restaurantName, boolean isOld) {
        super(id);
        this.restaurantName = restaurantName;
        this.isOld = isOld;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public boolean isOld() {
        return isOld;
    }
}
