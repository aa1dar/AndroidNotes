package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private Button getDateButton;
    private TextView text;

    private RecyclerView numList;
    private RecyclerView blocks;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RecycleView

        numList = findViewById(R.id.num_list);


        LinearLayoutManager layoutManager_of_numList = new LinearLayoutManager(this);
        numList.setLayoutManager(layoutManager_of_numList);
        numList.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(numList.getContext(),
                layoutManager_of_numList.getOrientation());
        numList.addItemDecoration(dividerItemDecoration);


        calendarView = findViewById(R.id.calendarView);


        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2021, 6, 5);

        try {
            calendarView.setDate(calendar1);
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }
        getDateButton = findViewById(R.id.getDateButton);


        getDateButton.setOnClickListener(v -> {
            for (Calendar calendar : calendarView.getSelectedDates()) {

                calendar.set(Calendar.HOUR, 0);
                Date date = calendar.getTime();


                listAdapter = new ListAdapter(24, date, this);
                numList.setAdapter(listAdapter);

            }

        });


    }


}