package ru.mirea.kozharinov.practice3.mireaproject.ui.calculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.mirea.kozharinov.practice3.mireaproject.databinding.FragmentCalculateBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculateFragment extends Fragment {

    private static final String TASK_INPUT_TEXT = "taskInputText";
    private static final String RESULT_TEXT = "resultText";
    private FragmentCalculateBinding binding;

    private String taskInputText;
    private String resultText;

    private String operand1, operand2;
    private Action action;
    private boolean isSecondOperandComplete = false;
    private boolean isActionPressed = false;
    private boolean isFirstOperandComplete = false;
    private int magicFlag = 0;

    public static CalculateFragment newInstance(String taskInputText, String resultText) {
        CalculateFragment fragment = new CalculateFragment();
        Bundle args = new Bundle();
        args.putString(TASK_INPUT_TEXT, taskInputText);
        args.putString(RESULT_TEXT, resultText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskInputText = getArguments().getString(TASK_INPUT_TEXT);
            resultText = getArguments().getString(RESULT_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CalculatorViewModel calculatorViewModel =
                new ViewModelProvider(this).get(CalculatorViewModel.class);

        binding = FragmentCalculateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.oneButton.setOnClickListener(this::onClickNumber);
        binding.twoButton.setOnClickListener(this::onClickNumber);
        binding.threeButton.setOnClickListener(this::onClickNumber);
        binding.fourButton.setOnClickListener(this::onClickNumber);
        binding.fiveButton.setOnClickListener(this::onClickNumber);
        binding.sixButton.setOnClickListener(this::onClickNumber);
        binding.sevenButton.setOnClickListener(this::onClickNumber);
        binding.eightButton.setOnClickListener(this::onClickNumber);
        binding.nineButton.setOnClickListener(this::onClickNumber);

        binding.addButton.setOnClickListener(view -> {
            Button button = (Button) view;
            binding.taskInputEditText.getText().append(button.getText());
            if (isSecondOperandComplete) {
                operand1 = String.valueOf(act());
            }
            action = Action.add;
            isActionPressed = true;
        });
        binding.subtractionButton.setOnClickListener(view -> {
            Button button = (Button) view;
            binding.taskInputEditText.getText().append(button.getText());
            if (isSecondOperandComplete) {
                operand1 = String.valueOf(act());
            }
            action = Action.subtract;
            isActionPressed = true;
        });
        binding.multiplyButton.setOnClickListener(view -> {
            Button button = (Button) view;
            binding.taskInputEditText.getText().append(button.getText());
            if (isSecondOperandComplete) {
                operand1 = String.valueOf(act());
            }
            action = Action.multiply;
            isActionPressed = true;
        });
        binding.divineButton.setOnClickListener(view -> {
            Button button = (Button) view;
            binding.taskInputEditText.getText().append(button.getText());
            if (isSecondOperandComplete) {
                operand1 = String.valueOf(act());
            }
            action = Action.divine;
            isActionPressed = true;
        });

        binding.calculateButton.setOnClickListener(view -> {
            String result;
            if (isSecondOperandComplete) {
                result = String.valueOf(act());
            } else {
                result = operand1;
            }
            binding.resultTextView.setText(result);
        });

        return root;
    }

    private void check() {
        magicFlag++;
        if (magicFlag % 2 == 0) {
            isSecondOperandComplete = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Bundle bundle = new Bundle();
        bundle.putString(TASK_INPUT_TEXT, binding.taskInputEditText.getText().toString());
        bundle.putString(RESULT_TEXT, binding.resultTextView.getText().toString());
        setArguments(bundle);
    }

    private void onClickNumber(View view) {
        Button button = (Button) view;
        if (!isActionPressed) {
            operand1 += button.getText();
        } else {
            operand2 += button.getText();
        }
        binding.taskInputEditText.getText().append(button.getText());
    }

    private int act() {
        switch (action) {
            case add:
                return Integer.parseInt(operand1) + Integer.parseInt(operand2);
            case subtract:
                return Integer.parseInt(operand1) - Integer.parseInt(operand2);
            case multiply:
                return Integer.parseInt(operand1) * Integer.parseInt(operand2);
            case divine:
                return Integer.parseInt(operand1) / Integer.parseInt(operand2);
            default:
                throw new IndexOutOfBoundsException();
        }
    }
}