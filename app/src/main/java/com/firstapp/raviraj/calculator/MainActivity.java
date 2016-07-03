package com.firstapp.raviraj.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView screen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = (TextView) findViewById(R.id.textView);
        screen.setText(display);
    }

    private void UpdateScreen() {
        screen.setText(display);
    }

    public void OnClickNumber(View v) {
        if (result != "") {
            Clear();
            UpdateScreen();
        }
        Button b = (Button) v;
        display += b.getText();
        UpdateScreen();
    }

    public void OnClickOperator(View v) {
        if (display == "") return;

        Button b = (Button) v;
        if (result != "") {
            String display1 = result;
            Clear();
            display = display1;
        }

        if (currentOperator != "") {
            if (IsOperator(display.charAt(display.length() - 1))) {
                display = display.replace(display.charAt(display.length() - 1), b.getText().charAt(0));
                UpdateScreen();
                return;
            } else {
                GetResult();
                display = result;
                result = "";
            }
            currentOperator = b.getText().toString();
        }

        display += b.getText();
        currentOperator = b.getText().toString();
        UpdateScreen();
    }

    private void Clear() {
        display = "";
        currentOperator = "";
        result = "";
    }

    private Boolean IsOperator(char op) {
        switch (op) {
            case '+':
            case '-':
            case 'x':
            case '/':
                return true;
            default:
                return false;
        }
    }

    private double Operate(String a, String b, String op) {
        switch (op) {
            case "+":
                return Double.valueOf(a) + Double.valueOf(b);
            case "-":
                return Double.valueOf(a) - Double.valueOf(b);
            case "x":
                return Double.valueOf(a) * Double.valueOf(b);
            case "/":
                try {
                    return Double.valueOf(a) / Double.valueOf(b);
                } catch (Exception e) {
                    Log.d("Calc", e.getMessage());
                }
            default:
                return -1;
        }

    }

    public void OnClickClear(View v) {
        Clear();
        UpdateScreen();
    }

    private Boolean GetResult() {
        if (currentOperator == "") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if (operation.length < 2) {
            return false;
        }
        result = String.valueOf(Operate(operation[0], operation[1], currentOperator));
        return true;
    }

    public void OnClickEqual(View v) {
        if (display == "") return;
        if (!GetResult()) {
            return;
        }
        screen.setText(display + "\n" + String.valueOf(result));
    }
}
