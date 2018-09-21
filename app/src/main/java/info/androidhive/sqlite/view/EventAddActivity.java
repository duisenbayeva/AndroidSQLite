package info.androidhive.sqlite.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;

import info.androidhive.sqlite.R;
import info.androidhive.sqlite.database.EventDatabaseHelper;
import info.androidhive.sqlite.database.model.Event;

public class EventAddActivity extends AppCompatActivity {

    private Button btnStore;
    private Calendar calendar;
    private TextView dateView;
    private EditText title, from, to;
    private int year, month, day;
    private int fHour, fMin, tHour, tMin;
    static final int DATE_DIALOG_ID = 999;

    private EventDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        databaseHelper = new EventDatabaseHelper(this);

        btnStore = findViewById(R.id.btnstore);
        title = findViewById(R.id.et_title);

        from = findViewById(R.id.et_from);
        to = findViewById(R.id.et_to);

        dateView = findViewById(R.id.et_date);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);



        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = title.getText().toString();
                if (TextUtils.isEmpty(name)){
                    title.setError("Enter title");
                    title.requestFocus();
                    return;
                }

                @SuppressLint("DefaultLocale") String timestamp = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00",
                        year, month+1, day)).toString();

                databaseHelper.insertEvent(new Event(title.getText().toString(),
                        timestamp,
                        from.getText().toString(),
                        to.getText().toString()));


                Toast.makeText(EventAddActivity.this, "Stored Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EventAddActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



    }


    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }

    public void showDatePicker(View v) {

        showDialog(DATE_DIALOG_ID);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            dateView.setText(new StringBuilder().append(year)
                    .append("-").append(month + 1).append("-").append(day)
                    .append(" "));

        }
    };

    public void showFromTimePicker(View v) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        fHour = c.get(Calendar.HOUR_OF_DAY);
        fMin = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        from.setText(hourOfDay + ":" + minute);
                    }
                }, fHour, fMin, false);
        timePickerDialog.show();

    }

    public void showToTimePicker(View v) {

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        tHour = c.get(Calendar.HOUR_OF_DAY);
        tMin = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        to.setText(hourOfDay + ":" + minute);
                    }
                }, tHour, tMin, false);
        timePickerDialog.show();

    }


}
