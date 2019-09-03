package ru.lunchvoter.data;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.lunchvoter.model.Vote;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lunchvoter.TestUtil.readFromJsonMvcResult;

public class VoteTestData {

    public static final long VOTE_ID = Vote.START_SEQ;
    public static final LocalDate DATE = LocalDate.now();

    public static final Vote OLD_VOTE = new Vote(VOTE_ID, UserTestData.USER_ID, RestaurantTestData.RESTAURANT_ID + 3, DATE);
    public static final Vote NEW_VOTE1 = new Vote(VOTE_ID, UserTestData.USER_ID, RestaurantTestData.RESTAURANT_ID, DATE);
    public static final Vote NEW_VOTE2 = new Vote(VOTE_ID + 1, UserTestData.USER_ID + 1, RestaurantTestData.RESTAURANT_ID, DATE);

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static ResultMatcher contentJson(Vote expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Vote.class), expected);
    }
}
