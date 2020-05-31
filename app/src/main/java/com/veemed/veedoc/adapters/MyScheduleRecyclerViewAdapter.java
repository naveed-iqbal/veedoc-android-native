package com.veemed.veedoc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.veedoc.R;
import com.veemed.veedoc.models.event.CalendarEvent;
import com.veemed.veedoc.models.event.OffDay;
import com.veemed.veedoc.models.event.OtherEvent;

import java.util.TreeMap;

public class MyScheduleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private TreeMap<Integer, CalendarEvent> eventDateAndDescription;
    private Integer[] dates;
    private CalendarEvent[] eventsArray;

    private static final int OFF_DAY_VIEW_TYPE = 0;
    private static final int DESCRIPTION_VIEW_TYPE = 1;

    public MyScheduleRecyclerViewAdapter(Context context, TreeMap<Integer, CalendarEvent> eventDateAndDescription) {
        this.context = context;
        if (eventDateAndDescription != null) {
            this.eventDateAndDescription = new TreeMap<>(eventDateAndDescription);
            dates = new Integer[this.eventDateAndDescription.size()];
            this.eventDateAndDescription.keySet().toArray(dates);
            eventsArray = new CalendarEvent[this.eventDateAndDescription.size()];
            this.eventDateAndDescription.values().toArray(eventsArray);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case OFF_DAY_VIEW_TYPE:
                View vOffDays = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_recyclerview_item_offday, parent, false);
                ViewHolderOffDays viewHolderOffDays = new ViewHolderOffDays(vOffDays);
                return viewHolderOffDays;
            case DESCRIPTION_VIEW_TYPE:
                View vEvent = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_recyclerview_item_event, parent, false);
                ViewHolderEvent viewHolderEvent = new ViewHolderEvent(vEvent);
                return viewHolderEvent;
        }
        return null; // error;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case OFF_DAY_VIEW_TYPE:
                if (eventDateAndDescription != null && eventDateAndDescription.size() > 0) {
                    ViewHolderOffDays holderOffDays = (ViewHolderOffDays) holder;
                    holderOffDays.dateNumber.setText(String.format("%02d", dates[position]));
                    holderOffDays.descriptionText.setText(eventsArray[position].getDescription());
                    // if (holderOffDays.descriptionText.getText().toString().equals("OFF")) {
                    holderOffDays.leftLineDivider.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), OffDay.MARKER_COLOR, null));
                    holderOffDays.descriptionText.setTextColor(ResourcesCompat.getColor(context.getResources(), OffDay.MARKER_COLOR, null));
                    // }
                }
                break;
            case DESCRIPTION_VIEW_TYPE:
                if (eventDateAndDescription != null && eventDateAndDescription.size() > 0) {
                    ViewHolderEvent holderEvent = (ViewHolderEvent) holder;
                    holderEvent.dateNumber.setText(String.format("%02d", dates[position]));
                    holderEvent.descriptionText.setText(eventsArray[position].getDescription());
                    holderEvent.timeIntervalText.setText(((OtherEvent) eventsArray[position]).getTimeInterval()); // casting object to other event
                    holderEvent.leftLineDivider.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), OtherEvent.MARKER_COLOR, null));
                    holderEvent.descriptionText.setTextColor(ResourcesCompat.getColor(context.getResources(), OtherEvent.MARKER_COLOR, null));
                    holderEvent.timeIntervalText.setTextColor(ResourcesCompat.getColor(context.getResources(), OtherEvent.MARKER_COLOR, null));
                }
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        // if the position of the array has a description that is off
        if (eventsArray[position] instanceof OffDay){
            return OFF_DAY_VIEW_TYPE;
        } else {
            return DESCRIPTION_VIEW_TYPE;
        }
    }


    // IDEA CREDITS: CodingWithMitch MVVM tutorial
    public void updateSchedule(TreeMap<Integer, CalendarEvent> eventDateAndDescription){
        this.eventDateAndDescription = new TreeMap<>(eventDateAndDescription); // make copy of linked hash map
        dates = new Integer[this.eventDateAndDescription.size()];
        this.eventDateAndDescription.keySet().toArray(dates);
        eventsArray = new CalendarEvent[this.eventDateAndDescription.size()];
        this.eventDateAndDescription.values().toArray(eventsArray);
        notifyDataSetChanged(); // this is so that the adapter will update the view with new data
    }

    @Override
    public int getItemCount() {
        return eventDateAndDescription==null?0:eventDateAndDescription.size();
    }

    public class ViewHolderOffDays extends RecyclerView.ViewHolder{

        RelativeLayout scheduleRecyclerViewItem;
        TextView dateNumber, descriptionText;
        View leftLineDivider;


        public ViewHolderOffDays(@NonNull View itemView) {
            super(itemView);
            scheduleRecyclerViewItem = itemView.findViewById(R.id.scheduleItemLayout_offDay);
            dateNumber = itemView.findViewById(R.id.dateNumberTextView_mySchedule);
            descriptionText = itemView.findViewById(R.id.eventDescriptionTextView_mySchedule);
            leftLineDivider = itemView.findViewById(R.id.leftLine_myScheduleRV);
        }

    }

    public class ViewHolderEvent extends RecyclerView.ViewHolder{

        RelativeLayout scheduleRecyclerViewItem;
        TextView dateNumber, descriptionText, timeIntervalText;
        View leftLineDivider;


        public ViewHolderEvent(@NonNull View itemView) {
            super(itemView);
            scheduleRecyclerViewItem = itemView.findViewById(R.id.scheduleItemLayout_event);
            dateNumber = itemView.findViewById(R.id.dateNumberTextView_mySchedule);
            descriptionText = itemView.findViewById(R.id.eventDescriptionTextView_mySchedule);
            leftLineDivider = itemView.findViewById(R.id.leftLine_myScheduleRV);
            timeIntervalText = itemView.findViewById(R.id.eventTimeIntervalTextView_mySchedule);
        }

    }
}
