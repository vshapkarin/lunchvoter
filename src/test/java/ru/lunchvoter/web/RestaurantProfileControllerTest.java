package ru.lunchvoter.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.lunchvoter.data.PositionTestData;
import ru.lunchvoter.data.VoteTestData;
import ru.lunchvoter.model.Restaurant;
import ru.lunchvoter.util.RestaurantUtil;
import ru.lunchvoter.util.exception.ErrorType;
import ru.lunchvoter.web.restaurant.RestaurantProfileController;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lunchvoter.TestUtil.userHttpBasic;
import static ru.lunchvoter.data.PositionTestData.DATE;
import static ru.lunchvoter.data.RestaurantTestData.*;
import static ru.lunchvoter.data.UserTestData.USER1;
import static ru.lunchvoter.data.UserTestData.USER2;
import static ru.lunchvoter.data.VoteTestData.NEW_VOTE2_TO;

class RestaurantProfileControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantProfileController.REST_URL + '/';

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RestaurantUtil.getTos(List.of(RESTAURANT1, RESTAURANT2, RESTAURANT3, RESTAURANT4))));
    }

    @Test
    void getAllUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getWithPositions() throws Exception {
        Restaurant restaurant = new Restaurant(RESTAURANT1);
        restaurant.setPositions(Set.of(PositionTestData.POSITION1, PositionTestData.POSITION2, PositionTestData.POSITION3));
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RestaurantUtil.getTo(restaurant, DATE)));
    }

    @Test
    void getWithPositionsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + 0)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.DATA_NOT_FOUND));
    }

    @Test
    void vote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT_ID)
                .with(userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.contentJson(NEW_VOTE2_TO));
    }

    @Test
    void voteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + 0)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.DATA_NOT_FOUND));
    }
}