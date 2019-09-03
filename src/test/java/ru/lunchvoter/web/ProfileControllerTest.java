package ru.lunchvoter.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoter.model.User;
import ru.lunchvoter.to.UserTo;
import ru.lunchvoter.util.UserUtil;
import ru.lunchvoter.util.exception.ErrorType;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lunchvoter.TestUtil.readFromJson;
import static ru.lunchvoter.TestUtil.userHttpBasic;
import static ru.lunchvoter.data.UserTestData.*;
import static ru.lunchvoter.web.user.ProfileController.REST_URL;

class ProfileControllerTest extends AbstractControllerTest {

    @Test
    void register() throws Exception {
        UserTo createdTo = new UserTo(null, "newName", "newuser@mail.com", "password");

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(createdTo, "password")))
                .andDo(print())
                .andExpect(status().isCreated());
        User returned = readFromJson(action, User.class);

        User created = UserUtil.getNewFromTo(createdTo);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(userService.getByEmail("newuser@mail.com"), created);
    }

    @Test
    void registerAuth() throws Exception {
        UserTo createdTo = new UserTo(null, "newName", "newuser@mail.com", "password");

        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .with(userHttpBasic(USER1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(createdTo, "password")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void registerInvalid() throws Exception {
        UserTo createdTo = new UserTo(null, null, "newuser@mail.com", "password");

        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(createdTo, "password")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void registerDuplicateEmail() throws Exception {
        UserTo createdTo = new UserTo(null, "newName", "admin@email.com", "password");

        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(createdTo, "password")))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(ErrorType.DATA_ERROR));
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(UserUtil.getTo(USER1)));
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), USER2, USER3, ADMIN);
    }

    @Test
    void update() throws Exception {
        UserTo updatedTo = new UserTo(null, "updatedUser", "updtd@mail.ru", "newPassword");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(jsonWithPassword(updatedTo, "newPassword")))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getByEmail("updtd@mail.ru"), UserUtil.updateFromTo(new User(USER1), updatedTo));
    }
}