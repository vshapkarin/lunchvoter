package ru.lunchvoter.web;

import ru.lunchvoter.model.User;

public class SecurityUtil {

    private static int id = User.START_SEQ;

    private SecurityUtil() {
    }

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }
}
