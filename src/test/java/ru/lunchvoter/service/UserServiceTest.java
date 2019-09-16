package ru.lunchvoter.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lunchvoter.AbstractServiceAndRepositoryTest;
import ru.lunchvoter.model.User;
import ru.lunchvoter.util.exception.NotFoundException;

import static ru.lunchvoter.data.UserTestData.*;

class UserServiceTest extends AbstractServiceAndRepositoryTest {

    private final UserService service;

    @Autowired
    UserServiceTest(UserService service) {
        this.service = service;
    }

    @Test
    void save() {
        User newUser = getCreated();
        User saved = service.save(newUser);
        assertMatch(saved, newUser);
        assertMatch(service.getAll(), USER1, USER2, USER3, ADMIN, newUser);
    }

    @Test
    void delete() {
        service.delete(USER_ID);
        assertMatch(service.getAll(), USER2, USER3, ADMIN);
    }

    @Test
    void get() {
        User actual = service.get(USER_ID);
        assertMatch(actual, USER1);
    }

    @Test
    void getByEmail() {
        User actual = service.getByEmail(USER1_EMAIL);
        assertMatch(actual, USER1);
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(), USERS);
    }

    @Test
    void deleteNotFound() {
        validateRootCause(() -> service.delete(0), NotFoundException.class);
    }

    @Test
    void getNotFound() {
        validateRootCause(() -> service.get(0), NotFoundException.class);
    }

    @Test
    void getByEmailNotFound() {
        validateRootCause(() -> service.getByEmail("dummy@mail.it"), NotFoundException.class);
    }
}
