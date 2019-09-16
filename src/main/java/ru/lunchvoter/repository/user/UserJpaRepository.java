package ru.lunchvoter.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoter.model.User;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserJpaRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);

    List<User> findAll();
}
