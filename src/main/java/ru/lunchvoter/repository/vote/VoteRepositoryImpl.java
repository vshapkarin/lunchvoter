package ru.lunchvoter.repository.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.lunchvoter.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class VoteRepositoryImpl {

    private final VoteJpaRepository jpaRepository;

    @Autowired
    public VoteRepositoryImpl(VoteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public Vote save(Vote vote) {
        return jpaRepository.save(vote);
    }

    public Optional<Vote> getByUserIdAndDate(int userId, LocalDate date) {
        return jpaRepository.findByUserIdAndDate(userId, date);
    }
}
