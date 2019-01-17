package com.tooc.android.tooc.util;

import com.google.gson.Gson;
import com.tooc.android.tooc.model.ErrorData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ErrorMessage {
    private static Gson gson;
    @NotNull
    public static ErrorData getErrorMessage(@Nullable String message) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(message,ErrorData.class);
    }
}
