package ru.lunchvoter.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoter.model.Vote;
import ru.lunchvoter.repository.vote.VoteRepositoryImpl;
import ru.lunchvoter.util.TimeUtil;
import ru.lunchvoter.util.VoteWrapper;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class VoteService {

    private final VoteRepositoryImpl repository;

    public VoteService(VoteRepositoryImpl repository) {
        this.repository = repository;
    }

    @Transactional
    public VoteWrapper vote(int userId, int restaurantId) {
        Vote oldVote = repository.getByUserIdAndDate(userId, LocalDate.now());
        Vote newVote = new Vote(userId, restaurantId, LocalDate.now());

        if (oldVote != null) {
            if(LocalTime.now().compareTo(TimeUtil.CHANGE_MIND_TIME) > 0) {
                return new VoteWrapper(oldVote, true);
            }
            newVote.setId(oldVote.getId());
        }
        return new VoteWrapper(newVote, false);
    }
}
