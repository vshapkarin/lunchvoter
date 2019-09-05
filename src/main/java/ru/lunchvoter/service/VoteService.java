package ru.lunchvoter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoter.model.Restaurant;
import ru.lunchvoter.model.User;
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
    public VoteWrapper vote(User user, int restaurantId, LocalDate voteDate, LocalTime changeMindTime) {
        Restaurant restaurant = restaurantRepository.get(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException(ValidationUtil.getWrongRestaurantMessage(restaurantId)));

        Vote oldVote = voteRepository.getByUserIdAndDate(user.getId(), voteDate).orElse(null);
        Vote newVote = new Vote(user, restaurant, voteDate);
        if (oldVote != null) {
            if(LocalTime.now().compareTo(changeMindTime) > 0) {
                return new VoteWrapper(oldVote, true);
            }
            newVote.setId(oldVote.getId());
        }
        return new VoteWrapper(voteRepository.save(newVote), false);
    }
}
