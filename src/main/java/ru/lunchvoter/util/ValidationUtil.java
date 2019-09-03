package ru.lunchvoter.util;

import ru.lunchvoter.HasId;
import ru.lunchvoter.util.exception.IllegalRequestDataException;

public class ValidationUtil {

    public static void assureIdConsistent(HasId<Integer> entityOrTo, int id) {
        if (entityOrTo.isNew()) {
                entityOrTo.setId(id);
        } else if (entityOrTo.getId() != id) {
            throw new IllegalRequestDataException(entityOrTo + " must be with id = " + id);
        }
    }

    public static void checkNew(HasId<Integer> entityOrTo) {
        if (!entityOrTo.isNew()) {
            throw new IllegalRequestDataException(entityOrTo + " must be new (id=null)");
        }
    }

    public static String getWrongRestaurantMessage(int restaurantId) {
        return "Not found restaurant with id = " + restaurantId;
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}
