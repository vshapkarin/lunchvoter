package ru.lunchvoter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoter.model.Vote;
import ru.lunchvoter.repository.restaurant.RestaurantRepository;
import ru.lunchvoter.repository.vote.VoteRepository;
import ru.lunchvoter.util.ValidationUtil;
import ru.lunchvoter.util.VoteWrapper;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public VoteWrapper vote(int userId, int restaurantId, LocalDate voteDate, LocalTime changeMindTime) {
        restaurantRepository.get(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException(ValidationUtil.getWrongRestaurantMessage(restaurantId)));

        Vote oldVote = voteRepository.getByUserIdAndDate(userId, voteDate).orElse(null);
        Vote newVote = new Vote(userId, restaurantId, voteDate);
        if (oldVote != null) {
            if(LocalTime.now().compareTo(changeMindTime) > 0) {
                return new VoteWrapper(oldVote, true);
            }
            newVote.setId(oldVote.getId());
        }
        return new VoteWrapper(voteRepository.save(newVote), false);
    }
}
