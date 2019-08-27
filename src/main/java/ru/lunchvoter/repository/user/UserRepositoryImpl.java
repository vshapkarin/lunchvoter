package ru.lunchvoter.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.lunchvoter.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl {

    private final UserJpaRepository jpaRepository;

    @Autowired
    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public User save(User user) {
        return jpaRepository.save(user);
    }

    public boolean delete(int id) {
        return jpaRepository.delete(id) != 0;
    }

    public Optional<User> get(int id) {
        return jpaRepository.findById(id);
    }

    public Optional<User> getByEmail(String email) {
        return jpaRepository.findByEmail(email);
    }

    public List<User> getAll() {
        return jpaRepository.findAll();
    }
}
