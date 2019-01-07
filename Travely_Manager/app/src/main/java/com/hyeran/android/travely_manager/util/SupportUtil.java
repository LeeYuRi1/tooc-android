package com.hyeran.android.travely_manager.util;

import com.google.gson.Gson;

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
