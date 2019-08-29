package ru.lunchvoter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoter.model.Vote;
import ru.lunchvoter.repository.restaurant.RestaurantRepositoryImpl;
import ru.lunchvoter.repository.vote.VoteRepositoryImpl;
import ru.lunchvoter.util.TimeUtil;
import ru.lunchvoter.util.VoteWrapper;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class VoteService {

    private final VoteRepositoryImpl voteRepository;

    private final RestaurantRepositoryImpl restaurantRepository;

    @Autowired
    public VoteService(VoteRepositoryImpl voteRepository, RestaurantRepositoryImpl restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public VoteWrapper vote(int userId, int restaurantId, LocalDate voteDate) {
        restaurantRepository.get(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Not found restaurant with id = " + restaurantId));

        Vote oldVote = voteRepository.getByUserIdAndDate(userId, voteDate).orElse(null);
        Vote newVote = new Vote(userId, restaurantId, voteDate);
        if (oldVote != null) {
            if(LocalTime.now().compareTo(TimeUtil.CHANGE_MIND_TIME) > 0) {
                return new VoteWrapper(oldVote, true);
            }
            newVote.setId(oldVote.getId());
        }
        return new VoteWrapper(voteRepository.save(newVote), false);
    }
}
