package com.twobonkers.hungry.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.twobonkers.hungry.data.models.User;

public class LocalUserRepository {

    private static final String TEMP_TOKEN = "  HI8TWT963CD1l6gdWfx0CbzPh-5hHKeoU8yuvq3KGEgpJp2TGbL0hy1FowH_IRg25duoKQh5iNvXgc7gD3e3mokc4LkMCAqNywTtoc7x6LRUWVelmo38VFAGk4N9fOJicrZsPfkFnjbiJjr64BTIw7lZUm_HXvrXvdWiCaXaLrH_VpLpalDitavgv2v3uCv26qNh2R25EToYuldxOo4LJVwLAWyk3NN1cGTuFC-olkb98G5x6ubkc0ff4piZnr53CzYEZtI6hbLuU3M28QeAz-BfG2TOlVyTy9juUwnxD5xUMv_ZQD85CfStgZK_bV2JBunfa1Ui9Sl1Sud1_TG2Hy1qQ9BbjNCrebwkIbXrxWLVKDO03UOw_KqAwINjO78cPjjjJYNaFS6LYtvBQvv_jaj9A3UthBtvhAHNoyu9Y4EwbByMyGmw9Rq7H6pLYEdGQzIB3PsD3ybnjhnd2SoE53gduHopC9UubyO3umDouZ2S44Y-ws6QL3fk9bWRphdV";

    private static final String USER_PREFERENCES = "com.twobonkers.hungry.user";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_AVATAR_URL = "avatarUrl";

    private Context context;
    private User user;

    public LocalUserRepository(Context context) {
        this.context = context;
    }

    public void setCurrentUser(User userData) {
        if (userData != null) {
            SharedPreferences prefs = context.getSharedPreferences(USER_PREFERENCES, 0);
            prefs.edit().putString(KEY_TOKEN, userData.token())
                    .putString(KEY_USERNAME, userData.username())
                    .apply();
        }

        user = userData;
    }

    public User currentUser() {
        if (user == null) {
            SharedPreferences prefs = context.getSharedPreferences(USER_PREFERENCES, 0);
            String token = prefs.getString(KEY_TOKEN, TEMP_TOKEN); // Temporarily return hardcoded token
            if (token == null) {
                return null;
            }

            user = User.builder()
                    .id(1)
                    .username(prefs.getString(KEY_USERNAME, "username"))
                    .token(token)
                    .build();
        }

        return user;
    }

    public void logout() {
        SharedPreferences prefs = context.getSharedPreferences(USER_PREFERENCES, 0);
        prefs.edit().clear().apply();
        user = null;
    }
}