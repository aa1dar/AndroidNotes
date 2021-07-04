package com.example.notes;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private static int ViewHolderCount;
    private int numberItems;
    private Date date_;
    public static json_parser arrays;
    private Set<Integer> eventsId;
    private Context main_context;

    public ListAdapter(int count_items, Date date, Context context) {
        numberItems = count_items;
        ViewHolderCount = 0;
        date_ = date;
        main_context = context;
        //data
        try {
            arrays = new json_parser(context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        arrays.CurDateList(date);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        //data


        //RecView
        int layoutIdItem = R.layout.number_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdItem, parent, false);

        ListViewHolder viewHolder = new ListViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.bind(position);
        holder.nameEvent.setText(arrays.namesOfDay.get(position));
        holder.timeEvent.setText(arrays.descriptionOfDay.get(position));
        int id = arrays.id_of_event.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (id != -1) {
                    Intent intent;
                    intent = new Intent(main_context, Description.class);
                    intent.putExtra("id_of_event", id);
                    main_context.startActivity(intent);

                }
            }

        });
    }


    @Override
    public int getItemCount() {

        return numberItems;
    }


    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView timeItem;
        TextView timeEvent;
        TextView nameEvent;

        String[] timesFields;
        public ArrayList<String> date_start = new ArrayList<>();
        public ArrayList<String> date_finish = new ArrayList<>();


        public ListViewHolder(View itemView) {
            super(itemView);

            timeItem = itemView.findViewById(R.id.time_item);
            timeEvent = itemView.findViewById(R.id.time_of_event);
            nameEvent = itemView.findViewById(R.id.name_of_event);
            timesFields = itemView.getResources().getStringArray(R.array.timesInterval);


        }

        void bind(int position) {
            timeItem.setText(timesFields[position]);

        }


    }
}
