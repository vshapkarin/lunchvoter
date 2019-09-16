package ru.lunchvoter.data;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.lunchvoter.model.Vote;
import ru.lunchvoter.to.VoteTo;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lunchvoter.TestUtil.readFromJsonMvcResult;

public class VoteTestData {

    public static final long VOTE_ID = Vote.START_SEQ;
    public static final LocalDate DATE = LocalDate.now();

    public static final VoteTo OLD_VOTE_TO = new VoteTo(VOTE_ID, RestaurantTestData.RESTAURANT4.getName(), true);
    public static final VoteTo NEW_VOTE1_TO = new VoteTo(VOTE_ID, RestaurantTestData.RESTAURANT1.getName(), false);
    public static final VoteTo NEW_VOTE2_TO = new VoteTo(VOTE_ID + 1, RestaurantTestData.RESTAURANT1.getName(), false);

    public static void assertMatch(VoteTo actual, VoteTo expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static ResultMatcher contentJson(VoteTo expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, VoteTo.class), expected);
    }
}
