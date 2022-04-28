package ru.mirea.kozharinov.practice3.mireaproject.ui.calculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.mariuszgromada.math.mxparser.Expression;

import ru.mirea.kozharinov.practice3.mireaproject.databinding.FragmentCalculateBinding;

public class CalculateFragment extends Fragment {

    public static final String SAVED_EXPRESSION = "saved_expression";
    private FragmentCalculateBinding binding;

    private String resultText;
    private String expression;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expression = "";
    }

    @Override
    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        if (getArguments() != null) {
            expression = bundle.getString(SAVED_EXPRESSION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalculateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.oneButton.setOnClickListener(this::onClick);
        binding.twoButton.setOnClickListener(this::onClick);
        binding.threeButton.setOnClickListener(this::onClick);
        binding.fourButton.setOnClickListener(this::onClick);
        binding.fiveButton.setOnClickListener(this::onClick);
        binding.sixButton.setOnClickListener(this::onClick);
        binding.sevenButton.setOnClickListener(this::onClick);
        binding.eightButton.setOnClickListener(this::onClick);
        binding.nineButton.setOnClickListener(this::onClick);
        binding.zeroButton.setOnClickListener(this::onClick);
        binding.addButton.setOnClickListener(this::onClick);
        binding.subtractionButton.setOnClickListener(this::onClick);
        binding.multiplyButton.setOnClickListener(this::onClick);
        binding.divineButton.setOnClickListener(this::onClick);

        binding.calculateButton.setOnClickListener(this::onClickCalculate);
        binding.clearButton.setOnClickListener(this::onClickClear);

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        Bundle bundle = new Bundle();
        bundle.putString(SAVED_EXPRESSION, expression);
        setArguments(bundle);
    }

    private void onClick(View view) {
        Button button = (Button) view;
        expression += button.getText().toString();
        binding.expression.setText(expression);
    }

    private void onClickClear(View view) {
        expression = "";
        binding.expression.setText(expression);
    }

    private void onClickCalculate(View view) {
        Expression expression1 = new Expression();
        expression1.setExpressionString(expression);
        double result = expression1.calculate();
        binding.resultTextView.setText(String.valueOf(result));
    }
}