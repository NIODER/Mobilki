package ru.mirea.kozharinov.practice3.mireaproject.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.Contract;

import ru.mirea.kozharinov.practice3.mireaproject.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    public static class Settings {

        public boolean setting1;
        public boolean setting2;
        public boolean setting3;
        public boolean switchIsOn;
        public String text;

        private static final String SETTING1 = "setting1";
        private static final String SETTING2 = "setting2";
        private static final String SETTING3 = "setting3";
        private static final String SWITCH = "switch";
        private static final String TEXT = "text";

        /**
         * Создание настроек по заданным значениям
         * @param setting1 default false
         * @param setting2 default false
         * @param setting3 default false
         * @param switchIsOn default false
         * @param text default ""
         */
        public Settings(boolean setting1,
                        boolean setting2,
                        boolean setting3,
                        boolean switchIsOn,
                        String text) {
            this.setting1 = setting1;
            this.setting2 = setting2;
            this.setting3 = setting3;
            this.switchIsOn = switchIsOn;
            this.text = text;
        }

        /**
         * Создание настроек по готовому SharedPreferences файлу,
         * если параметр не найден, присваивается default
         * @param preferences saved preferences
         */
        public Settings(@NonNull SharedPreferences preferences) {
            setting1 = preferences.getBoolean(SETTING1, false);
            setting2 = preferences.getBoolean(SETTING2, false);
            setting3 = preferences.getBoolean(SETTING3, false);

            switchIsOn = preferences.getBoolean(SWITCH, false);

            text = preferences.getString(TEXT, "");
        }

        /**
         * Настройки со значениями по умолчанию
         * @return new Settings(false, false, false, false, "");
         */
        @NonNull
        @Contract(value = " -> new", pure = true)
        public static Settings getDefault() {
            return new Settings(false, false, false, false, "");
        }

        /**
         * Создает и записывает preferences с имеющимися значениями
         * @param preferences preferences для которых надо получить editor
         * @return созданный editor
         */
        public SharedPreferences.Editor getEditor(@NonNull SharedPreferences preferences) {
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(SETTING1, setting1);
            editor.putBoolean(SETTING2, setting2);
            editor.putBoolean(SETTING3, setting3);
            editor.putBoolean(SWITCH, switchIsOn);
            editor.putString(TEXT, text);
            return editor;
        }
    }

    private FragmentSettingsBinding binding;
    private SharedPreferences preferences;
    private MutableLiveData<Settings> settingsMutableLiveData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        binding.saveButton.setOnClickListener(this::onClickSaveButton);
        settingsMutableLiveData = new MutableLiveData<>();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        settingsMutableLiveData.setValue(new Settings(preferences));
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        Settings settings = settingsMutableLiveData.getValue();
        if (settings == null) {
            settings = new Settings(preferences);
        }
        binding.setting1.setChecked(settings.setting1);
        binding.setting2.setChecked(settings.setting2);
        binding.setting3.setChecked(settings.setting3);
        binding.switch1.setChecked(settings.switchIsOn);
        binding.editTextName.setText(settings.text);
        super.onResume();
    }

    @Override
    public void onPause() {
        settingsMutableLiveData.setValue(
                new Settings(
                        binding.setting1.isChecked(),
                        binding.setting2.isChecked(),
                        binding.setting3.isChecked(),
                        binding.switch1.isChecked(),
                        binding.editTextName.getText().toString()
                )
        );
        super.onPause();
    }

    private void onClickSaveButton(View view) {
        Settings settings = new Settings(
                binding.setting1.isChecked(),
                binding.setting2.isChecked(),
                binding.setting3.isChecked(),
                binding.switch1.isChecked(),
                binding.editTextName.getText().toString()
        );
        SharedPreferences.Editor editor = settings.getEditor(preferences);
        editor.apply();
    }
}
