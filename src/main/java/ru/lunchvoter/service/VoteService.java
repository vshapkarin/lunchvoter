package ru.lunchvoter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.lunchvoter.model.Restaurant;
import ru.lunchvoter.model.User;
import ru.lunchvoter.model.Vote;
import ru.lunchvoter.repository.restaurant.RestaurantRepositoryImpl;
import ru.lunchvoter.repository.vote.VoteJpaRepository;
import ru.lunchvoter.to.VoteTo;
import ru.lunchvoter.util.ValidationUtil;
import ru.lunchvoter.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class VoteService {

    private final VoteJpaRepository voteRepository;

    private final RestaurantRepositoryImpl restaurantRepository;

    @Autowired
    public VoteService(VoteJpaRepository voteRepository, RestaurantRepositoryImpl restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public VoteTo vote(User user, int restaurantId, LocalDate voteDate, LocalTime changeMindTime) {
        Restaurant restaurant = restaurantRepository.getWithPositionsByDate(restaurantId, voteDate)
                .orElseThrow(() -> new NotFoundException(ValidationUtil.getWrongRestaurantMessage(restaurantId)));
        if (CollectionUtils.isEmpty(restaurant.getPositions())) {
            throw new NotFoundException("There is no menu to vote for");
        }

        Vote oldVote = voteRepository.findByUserIdAndDate(user.getId(), voteDate);
        Vote newVote = new Vote(user, restaurant, voteDate);
        if (oldVote != null) {
            if(LocalTime.now().isAfter(changeMindTime)) {
                return new VoteTo(oldVote.getId(), oldVote.getRestaurant().getName(), true);
            }
            newVote.setId(oldVote.getId());
        }
        return new VoteTo(voteRepository.save(newVote).getId(), restaurant.getName(), false);
    }
}
