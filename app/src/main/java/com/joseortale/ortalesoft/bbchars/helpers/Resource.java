package com.joseortale.ortalesoft.bbchars.helpers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {
    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable
    public final String message;
    @Nullable
    public final Integer responseCode;

    private Resource(@NonNull Status status, @Nullable T data,
                     @Nullable String message, @Nullable Integer responseCode) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.responseCode = responseCode;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null, 200);
    }

    public static <T> Resource<T> error(@Nullable String msg, @Nullable T data, @Nullable Integer responseConde) {
        return new Resource<>(Status.ERROR, data, msg, responseConde);
    }

    public enum Status {SUCCESS, ERROR}
}
