package ru.lunchvoter.util;

import org.springframework.util.Assert;
import ru.lunchvoter.model.AbstractEntity;

public class ValidationUtil {

    public static void assureIdConsistent(AbstractEntity<Integer> entity, int id) {
        if (entity.isNew()) {
                entity.setId(id);
        } else {
            //noinspection ConstantConditions
            Assert.isTrue(entity.getId() == id, entity + " must be with id = " + id);
        }
    }

    public static void checkNew(AbstractEntity<Integer> entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }
}
