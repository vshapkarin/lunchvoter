package ru.lunchvoter.repository.vote;

import ru.lunchvoter.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

public interface VoteRepository {

    Vote save(Vote vote);

    Optional<Vote> getByUserIdAndDate(int userId, LocalDate date);
}
