package ru.lunchvoter.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoter.model.Restaurant;
import ru.lunchvoter.repository.restaurant.RestaurantRepository;
import ru.lunchvoter.to.RestaurantTo;
import ru.lunchvoter.util.RestaurantUtil;
import ru.lunchvoter.util.exception.ErrorType;
import ru.lunchvoter.web.json.JsonUtil;
import ru.lunchvoter.web.restaurant.RestaurantAdminController;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lunchvoter.TestUtil.readFromJson;
import static ru.lunchvoter.TestUtil.userHttpBasic;
import static ru.lunchvoter.data.PositionTestData.*;
import static ru.lunchvoter.data.RestaurantTestData.assertMatch;
import static ru.lunchvoter.data.RestaurantTestData.*;
import static ru.lunchvoter.data.UserTestData.ADMIN;
import static ru.lunchvoter.data.UserTestData.USER1;

class RestaurantAdminControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantAdminController.REST_URL + '/';

    @Autowired
    private RestaurantRepository repository;

    @Test
    void createWithLocation() throws Exception {
        RestaurantTo createdTo = new RestaurantTo(null, "newRestaurant");

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(createdTo)))
                .andDo(print())
                .andExpect(status().isCreated());
        Restaurant returned = readFromJson(action, Restaurant.class);

        int returnedId = returned.getId();
        createdTo.setId(returnedId);
        Restaurant created = RestaurantUtil.getFromTo(createdTo);

        assertMatch(returned, created);
        assertMatch(repository.get(returnedId).orElse(null), created);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        RestaurantTo createdTo = new RestaurantTo(null, RESTAURANT1.getName());

        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(createdTo)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(ErrorType.DATA_ERROR));
    }

    @Test
    void createInvalid() throws Exception {
        RestaurantTo createdTo = new RestaurantTo(null, null);

        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(createdTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR));
    }

    @Test
    void createUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(repository.getAll(), RESTAURANT2, RESTAURANT3, RESTAURANT4);
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + 0)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.DATA_NOT_FOUND));
    }

    @Test
    void update() throws Exception {
        RestaurantTo updatedTo = new RestaurantTo(RESTAURANT_ID, "updatedRestaurant");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(repository.get(RESTAURANT_ID).orElse(null), RestaurantUtil.getFromTo(updatedTo));
    }

    @Test
    void updateMenu() throws Exception {
        RestaurantTo updatedTo = new RestaurantTo();
        updatedTo.setMenuDate(DATE);
        updatedTo.setMenu(getMenu());

        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Assertions.assertThat(repository.getWithPositionsByDate(RESTAURANT_ID, DATE).orElseThrow().getPositions())
                .isEqualTo(Set.of(UPDATED_POSITION));
    }

    @Test
    void updateMenuInvalid() throws Exception {
        RestaurantTo updatedTo = new RestaurantTo();

        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR));
    }

    @Test
    void updateMenuRestaurantNotFound() throws Exception {
        RestaurantTo updatedTo = new RestaurantTo();
        updatedTo.setMenuDate(DATE);
        updatedTo.setMenu(getMenu());

        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + 0)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.DATA_NOT_FOUND));
    }
}