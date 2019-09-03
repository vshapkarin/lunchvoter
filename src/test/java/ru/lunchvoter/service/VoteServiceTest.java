package ru.lunchvoter.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lunchvoter.AbstractServiceAndRepositoryTest;
import ru.lunchvoter.data.RestaurantTestData;
import ru.lunchvoter.util.TimeUtil;
import ru.lunchvoter.util.VoteWrapper;

import java.time.LocalTime;

import static ru.lunchvoter.data.UserTestData.USER_ID;
import static ru.lunchvoter.data.VoteTestData.*;

class VoteServiceTest extends AbstractServiceAndRepositoryTest {

    private final VoteService service;

    @Autowired
    VoteServiceTest(VoteService service) {
        this.service = service;
    }

    @Test
    void vote() {
        VoteWrapper saved = service.vote(USER_ID + 1, RestaurantTestData.RESTAURANT_ID, DATE, TimeUtil.CHANGE_MIND_TIME);
        Assertions.assertThat(saved.isOld()).isEqualTo(false);
        assertMatch(saved.getVote(), NEW_VOTE2);
    }

    @Test
    void voteAgainBeforeChangeMindTime() {
        VoteWrapper saved = service.vote(USER_ID, RestaurantTestData.RESTAURANT_ID, DATE, LocalTime.MAX);
        Assertions.assertThat(saved.isOld()).isEqualTo(false);
        assertMatch(saved.getVote(), NEW_VOTE1);
    }

    @Test
    void voteAgainAfterChangeMindTime() {
        VoteWrapper saved = service.vote(USER_ID, RestaurantTestData.RESTAURANT_ID, DATE, LocalTime.MIN);
        Assertions.assertThat(saved.isOld()).isEqualTo(true);
        assertMatch(saved.getVote(), OLD_VOTE);
    }

    @Test
    void voteWrongRestaurant() {
        validateRootCause(
                () -> service.vote(USER_ID + 1, 0, DATE, TimeUtil.CHANGE_MIND_TIME),
                IllegalArgumentException.class);
    }
}
