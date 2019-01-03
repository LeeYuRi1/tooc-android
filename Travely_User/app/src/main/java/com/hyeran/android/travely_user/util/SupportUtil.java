package com.hyeran.android.travely_user.util;

import com.google.gson.Gson;
import com.hyeran.android.travely_user.model.ErrorData;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SupportUtil {
    private static Gson gson;
    @NotNull
    public static ErrorData getErrorMessage(@Nullable String message) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(message,ErrorData.class);
    }
}
