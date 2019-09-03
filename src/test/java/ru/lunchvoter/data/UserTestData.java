package ru.lunchvoter.data;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.lunchvoter.model.Role;
import ru.lunchvoter.model.User;
import ru.lunchvoter.to.UserTo;
import ru.lunchvoter.web.json.JsonUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lunchvoter.TestUtil.readFromJsonMvcResult;
import static ru.lunchvoter.TestUtil.readListFromJsonMvcResult;

public class UserTestData {

    public static final int USER_ID = User.START_SEQ;

    public static final String USER1_EMAIL = "johnycowboy@email.com";

    public static final User USER1 = new User(USER_ID, "John", USER1_EMAIL, "password", Role.ROLE_USER);
    public static final User USER2 = new User(USER_ID + 1, "Bob", "builderbob@email.com", "password", Role.ROLE_USER);
    public static final User USER3 = new User(USER_ID + 2, "Christine", "chrstn@email.com", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(USER_ID + 3, "Admin", "admin@email.com", "admin", Role.ROLE_USER, Role.ROLE_ADMIN);

    public static final List<User> USERS = List.of(USER1, USER2, USER3, ADMIN);

    public static User getCreated() {
        return new User(null, "newUser", "user@yandex.ru", "password", Role.ROLE_USER);
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "password", "registered", "votes");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("password", "registered", "votes").isEqualTo(expected);
    }

    public static void assertMatch(UserTo actual, UserTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "password");
    }

    public static ResultMatcher contentJson(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

    public static ResultMatcher contentJson(User expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
    }

    public static ResultMatcher contentJson(UserTo expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, UserTo.class), expected);
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }

    public static String jsonWithPassword(UserTo userTo, String passw) {
        return JsonUtil.writeAdditionProps(userTo, "password", passw);
    }
}
