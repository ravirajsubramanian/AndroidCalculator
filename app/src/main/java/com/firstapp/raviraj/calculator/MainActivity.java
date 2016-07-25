package com.firstapp.raviraj.calculator;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class  MainActivity extends AppCompatActivity {


    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    private TextView screen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView)findViewById(R.id.nav_view);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout,
                toolbar, R.string.app_name, R.string.app_name) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
               // getActionBar().setTitle(R.string.app_name);
               // invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
               // getActionBar().setTitle("Opened");
              //  invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                int id = menuItem.getItemId();
                switch (id) {

                    case R.id.nav_camera:
                        Toast toast = Toast.makeText(MainActivity.this, "Import", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP| Gravity.CENTER, 0, 0);
                        toast.show();
                        return true;

                    case R.id.nav_gallery:
                        Toast toast1 = Toast.makeText(MainActivity.this, "Gallery", Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.TOP| Gravity.CENTER, 0, 0);
                        toast1.show();
                        return true;
                }

                return false;
            }


        });

        screen = (TextView) findViewById(R.id.textView);
        screen.setText(display);
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }
    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
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
