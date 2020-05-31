package com.veemed.veedoc.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.veemed.veedoc.R;
import com.veemed.veedoc.adapters.CalendarDecorator;
import com.veemed.veedoc.models.event.CalendarEvent;
import com.veemed.veedoc.models.event.OffDay;
import com.veemed.veedoc.models.event.OtherEvent;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;
import com.veemed.veedoc.viewmodels.ScheduleViewModel;

import java.util.Calendar;
import java.util.List;

public class MyOffDaysFragment extends Fragment {

    private NavigationActivityViewModel navigationActivityViewModel;
    private ScheduleViewModel scheduleViewModel;
    private MaterialCalendarView markOffDaysCalendarView;
    private Button doneButton;

    public static MyOffDaysFragment newInstance() {
        return new MyOffDaysFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.off_days_fragment, container, false);
        markOffDaysCalendarView = v.findViewById(R.id.calendarView_offDays);
        doneButton = v.findViewById(R.id.doneButton_markOffDays);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // passing getActivity() to allow communication with navigation activity class
        scheduleViewModel = ViewModelProviders.of(getActivity()).get(ScheduleViewModel.class);
        navigationActivityViewModel = ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);

        // set center text view visible to true
        navigationActivityViewModel.setCenterTextViewVisible(true);
        navigationActivityViewModel.setToolbarTitle("Mark Off Days");
        navigationActivityViewModel.setToolbarSubtitle(null);

        loadAlreadyMarkedDays();

        setObservers();

        setClickers();

    }

    private void setObservers(){
        markOffDaysCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                // if a date has been selected
                if (selected) {
                    // check if the date already has (any kind) a mark. if there is no mark
                    if (!scheduleViewModel.isDateAlreadyMarked(date)) {
                        // make a new red mark, signifying an off day
                        CalendarDecorator decorator = new CalendarDecorator(date, ContextCompat.getColor(getActivity(), OffDay.MARKER_COLOR));
                        // store the date for which we put a red mark
                        scheduleViewModel.addDecoratorMapping(date, decorator);
                        // add the red mark to the calendar
                        markOffDaysCalendarView.addDecorator(decorator);
                        // store the date we put the mark on
                        scheduleViewModel.addTempUserOffDay(date);
                    } else if (!scheduleViewModel.isEventDay(date)) { // if the date is marked and is and event day
                        // make dot disappear
                        markOffDaysCalendarView.removeDecorator(scheduleViewModel.getDecoratorForDate(date));
                        scheduleViewModel.removeTempUserOffDay(date); // i.e. save that this date is no longer marked
                    }
                }
                // make button visible
                doneButton.setVisibility(View.VISIBLE);
            }


        });

    }

    private void setClickers(){
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add the off dates selected to the users event dates
               // navigationActivityViewModel.setUserOffDays();
                scheduleViewModel.setUserOffDays();
            }
        });
    }

    private void loadAlreadyMarkedDays() {
        Calendar c = Calendar.getInstance();
        scheduleViewModel.getOffDays(c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1).observe(this, new Observer<List<CalendarEvent>>() {
            @Override
            public void onChanged(List<CalendarEvent> calendarEvents) {
                for (CalendarEvent date : calendarEvents){
                    // get the current date
                    CalendarDay calendarDayForDate = date.getEventDate();
                    CalendarDecorator decorator;
                    //if (scheduleViewModel.isOffDay(date)){ // if the date is an off day
                        // make a red dot
                    decorator = new CalendarDecorator(calendarDayForDate, ContextCompat.getColor(getActivity(), OffDay.MARKER_COLOR));
                    /*} else { // if any other event
                        // make a blue dot
                        decorator = new CalendarDecorator(calendarDayForDate, ContextCompat.getColor(getActivity(), OtherEvent.MARKER_COLOR));
                    }*/

                    // save the date to decorator mapping
                    scheduleViewModel.addDecoratorMapping(calendarDayForDate, decorator);
                    // make the dot
                    markOffDaysCalendarView.addDecorator(decorator);
                    // add the date to the off days
                    scheduleViewModel.addTempUserOffDay(calendarDayForDate); // i.e. save marked date
                }
            }
        });


    }

    @Override
    public void onPause() {
        // clear the temp days when fragment is put on pause (i.e. back pressed or it is left in any case)
       // navigationActivityViewModel.clearTempOffDays();
        scheduleViewModel.clearTempOffDays();
        super.onPause();
    }
}
