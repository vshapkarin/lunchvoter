package ru.lunchvoter.repository.user;

import ru.lunchvoter.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    boolean delete(int id);

    Optional<User> get(int id);

    Optional<User> getByEmail(String email);

    List<User> getAll();
}
