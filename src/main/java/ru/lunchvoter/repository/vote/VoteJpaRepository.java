package ru.lunchvoter.repository.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoter.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteJpaRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUserIdAndDate(int userId, LocalDate date);
}
