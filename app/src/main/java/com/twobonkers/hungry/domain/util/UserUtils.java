package com.twobonkers.hungry.domain.util;

import com.twobonkers.hungry.data.models.User;

public class UserUtils {

    public static String getBearerToken(User user) {
        if (user == null) {
            return null;
        }

        return "Bearer " + user.token();
    }
}
