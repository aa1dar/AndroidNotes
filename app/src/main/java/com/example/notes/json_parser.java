package com.example.notes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TimerTask;


public class json_parser {

    private final Context context_;
    public ArrayList<String> date_start = new ArrayList<>();
    public ArrayList<String> date_finish = new ArrayList<>();
    public ArrayList<String> name = new ArrayList<>();
    public ArrayList<String> description = new ArrayList<>();
    public ArrayList<String> namesOfDay = new ArrayList<String>();
    public ArrayList<String> descriptionOfDay = new ArrayList<String>();
    public ArrayList<Integer> id_of_event = new ArrayList<Integer>();


    json_parser(Context context) throws JSONException {
        context_ = context;

        try {
            JSONObject jsonObject = new JSONObject(JsonDataFromAsset("events.json"));
            JSONArray jsonArray = jsonObject.getJSONArray("events");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userData = jsonArray.getJSONObject(i);
                date_start.add(userData.getString("date_start"));
                date_finish.add(userData.getString("date_finish"));
                description.add(userData.getString("description"));
                name.add(userData.getString("name"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String JsonDataFromAsset(String fileName) {
        String json = null;
        try {
            InputStream inputStream = context_.getAssets().open(fileName);
            int sizeOfFile = inputStream.available();
            byte[] bufferData = new byte[sizeOfFile];
            inputStream.read(bufferData);
            inputStream.close();
            json = new String(bufferData, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    Timestamp startDateInterval(Date date) {
        Timestamp timestamp = new Timestamp(date.getTime());
        System.out.println("time: " + timestamp.toString());
        return timestamp;
    }

    Timestamp endDateInterval(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
        System.out.println("time: " + timestamp.toString());
        return timestamp;
    }

    void CurDateList(Date date) {
        Set<Integer> eventId = new HashSet<Integer>();
        Timestamp startDay = startDateInterval(date);
        Timestamp endDay = endDateInterval(date);

        int id = 0;
        for (String main_date : date_start) {
            long timee = Long.parseLong(main_date) * 1000;
            Timestamp event = new Timestamp(timee);
            System.out.println("event tine: " + timee + "  : " + event.toString());

            if (event.after(startDay) && (event.before(endDay))) {
                eventId.add(id);
            }
            id++;

        }
        id = 0;
        for (String main_date : date_finish) {
            long timee = Long.parseLong(main_date) * 1000;
            Timestamp event = new Timestamp(timee);

            if (event.after(startDay) && (event.before(endDay))) {
                eventId.add(id);
            }
            id++;

        }

        getDayND(eventId);
    }

    void InitialArray() {
        for (int i = 0; i < 24; i++) {
            namesOfDay.add("");
            descriptionOfDay.add("");
            id_of_event.add(-1);
        }
    }

    void getDayND(Set<Integer> ids) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        InitialArray();

        for (int id : ids) {
            String time = date_start.get(id);
            long timee = Long.parseLong(time) * 1000;
            Timestamp event = new Timestamp(timee);
            System.out.println("Событие нашлось: " + event.toString());
            calendar.setTime(event);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            System.out.println("Время: " + hour);
            namesOfDay.set(hour, name.get(id));
            descriptionOfDay.set(hour, sdf.format(event));
            id_of_event.set(hour, id);

        }

    }


}
