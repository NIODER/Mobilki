package ru.mirea.kozharinov.practice3.mireaproject.ui.calculator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalculatorViewModel extends ViewModel {

    private final MutableLiveData<String> taskInputText;
    private final MutableLiveData<String> resultText;

    public CalculatorViewModel() {
        this.taskInputText = new MutableLiveData<>();
        this.resultText = new MutableLiveData<>();
    }
}
