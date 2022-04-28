package ru.mirea.kozharinov.practice4.loadermanager;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.Random;

public class MyLoader extends AsyncTaskLoader<String> {

    private String text;
    public static final String ARG_WORD = "text";

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null) {
            text = args.getString(ARG_WORD, "");
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        if (text == "") {
            return "ошибка ввода";
        } else {
            return shuffle(text);
        }
    }

    private String shuffle(@NonNull String s) {
        char[] chars = s.toCharArray();
        Random random = new Random();
        for (int i = chars.length - 1; i > 1; i--) {
            int j = random.nextInt(i - 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }
}
