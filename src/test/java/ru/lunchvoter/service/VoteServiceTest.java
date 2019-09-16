package ru.lunchvoter.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lunchvoter.AbstractServiceAndRepositoryTest;
import ru.lunchvoter.data.RestaurantTestData;
import ru.lunchvoter.util.TimeUtil;
import ru.lunchvoter.to.VoteTo;
import ru.lunchvoter.util.exception.NotFoundException;

import java.time.LocalTime;

import static ru.lunchvoter.data.UserTestData.USER1;
import static ru.lunchvoter.data.UserTestData.USER2;
import static ru.lunchvoter.data.VoteTestData.*;

class VoteServiceTest extends AbstractServiceAndRepositoryTest {

    private final VoteService service;

    @Autowired
    VoteServiceTest(VoteService service) {
        this.service = service;
    }

    @Test
    void vote() {
        VoteTo saved = service.vote(USER2, RestaurantTestData.RESTAURANT_ID, DATE, TimeUtil.CHANGE_MIND_TIME);
        Assertions.assertThat(saved.isOld()).isEqualTo(false);
        assertMatch(saved, NEW_VOTE2_TO);
    }

    @Test
    void voteAgainBeforeChangeMindTime() {
        VoteTo saved = service.vote(USER1, RestaurantTestData.RESTAURANT_ID, DATE, LocalTime.MAX);
        Assertions.assertThat(saved.isOld()).isEqualTo(false);
        assertMatch(saved, NEW_VOTE1_TO);
    }

    @Test
    void voteAgainAfterChangeMindTime() {
        VoteTo saved = service.vote(USER1, RestaurantTestData.RESTAURANT_ID, DATE, LocalTime.MIN);
        Assertions.assertThat(saved.isOld()).isEqualTo(true);
        assertMatch(saved, OLD_VOTE_TO);
    }

    @Test
    void voteWrongRestaurant() {
        validateRootCause(
                () -> service.vote(USER2, 0, DATE, TimeUtil.CHANGE_MIND_TIME),
                NotFoundException.class);
    }

    @Test
    void voteEmptyMenu() {
        validateRootCause(
                () -> service.vote(USER2, RestaurantTestData.RESTAURANT_ID + 2, DATE, TimeUtil.CHANGE_MIND_TIME),
                NotFoundException.class);
    }
}
