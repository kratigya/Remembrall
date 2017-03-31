package com.example.hppc.googlecalendartask;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText startDate, startTime, endDate, endTime, desc;
    private Button submit;
    private DatePickerDialog startDatePicker, endDatePicker;
    private TimePickerDialog startTimePicker, endTimePicker;
    private SimpleDateFormat dateFormatter;
    private Calendar start, stDate, end;
    private String eventDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startDate = (EditText) findViewById(R.id.startDate);
        startTime = (EditText) findViewById(R.id.startTime);
        endDate = (EditText) findViewById(R.id.endDate);
        endTime = (EditText) findViewById(R.id.endTime);
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        stDate = Calendar.getInstance();

        Calendar cal = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

        startDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newCal = Calendar.getInstance();
                newCal.set(year,month,dayOfMonth);
                start.set(year, month, dayOfMonth);
                stDate.set(year, month, dayOfMonth, 0, 0);
                startDate.setText(dateFormatter.format(newCal.getTime()));
            }
        },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));

        startTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                start.set(Calendar.HOUR, hourOfDay);
                start.set(Calendar.MINUTE, minute);
                startTime.setText(hourOfDay + ":" + minute);
            }
        }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), false);

        endTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                end.set(Calendar.HOUR, hourOfDay);
                end.set(Calendar.MINUTE, minute);
                if (end.after(start)) {
                    endTime.setText(hourOfDay + ":" + minute);
                } else {
                    Toast.makeText(MainActivity.this, "Wrong Time", Toast.LENGTH_LONG).show();
                }

            }
        }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), false);


        endDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newCal = Calendar.getInstance();
                newCal.set(year,month,dayOfMonth);
                end.set(year, month, dayOfMonth, 0, 0);
                if (end.equals(stDate) || end.after(start)) {
                    endDate.setText(dateFormatter.format(newCal.getTime()));
                } else {
                    Toast.makeText(MainActivity.this, "Wrong Date", Toast.LENGTH_LONG).show();
                }

            }
        },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));

        startDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                startDatePicker.show();
                return true;
            }
        });


        endDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                endDatePicker.show();
                return true;
            }
        });

        startTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                startTimePicker.show();
                return true;
            }
        });

        endTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                endTimePicker.show();
                return true;
            }
        });
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
