package ru.lunchvoter.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.lunchvoter.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Autowired
    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        return jpaRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return jpaRepository.delete(id) != 0;
    }

    @Override
    public Optional<User> get(int id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return jpaRepository.findByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return jpaRepository.findAll();
    }
}
