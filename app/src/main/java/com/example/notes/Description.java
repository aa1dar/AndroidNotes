package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Description extends AppCompatActivity {
    int id;
    TextView time;
    TextView description;
    TextView name;
    json_parser data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            id = extras.getInt("id_of_event");
        }
        data = ListAdapter.arrays;

        time = findViewById(R.id.description_time);
        name = findViewById(R.id.description_name);
        description = findViewById(R.id.description_description);


        name.setText(data.name.get(id));
        description.setText(data.description.get(id));


        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        String timeStart = data.date_start.get(id);
        String timeEnd = data.date_finish.get(id);

        Timestamp timeSt = new Timestamp(Long.parseLong(timeStart)*1000);
        Timestamp timeFi = new Timestamp(Long.parseLong(timeEnd)*1000);


        time.setText(sdf.format(timeSt));
        time.append(" - "+ sdf.format(timeFi));



    }
}