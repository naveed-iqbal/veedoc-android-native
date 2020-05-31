package com.veemed.veedoc.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarUtils;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.veemed.veedoc.R;
import com.veemed.veedoc.adapters.CalendarDecorator;
import com.veemed.veedoc.adapters.MyScheduleRecyclerViewAdapter;
import com.veemed.veedoc.models.event.CalendarEvent;
import com.veemed.veedoc.models.event.OffDay;
import com.veemed.veedoc.models.event.OffDayModel;
import com.veemed.veedoc.models.event.OtherEvent;
import com.veemed.veedoc.models.User;
import com.veemed.veedoc.models.event.ScheduledEvent;
import com.veemed.veedoc.utils.ReturnResponse;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;
import com.veemed.veedoc.viewmodels.ScheduleViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

public class ScheduleFragment extends Fragment {

    private NavigationActivityViewModel navigationActivityViewModel;
    private ScheduleViewModel scheduleViewModel;
    private MaterialCalendarView scheduleCalenderView;
    private MyScheduleRecyclerViewAdapter myScheduleRecyclerViewAdapter;
    private RecyclerView myScheduleRecyclerView;

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.schedule_fragment, container, false);
        scheduleCalenderView = v.findViewById(R.id.calendarView_mySchedule);
        initializeRecyclerView(v);
        return v;
    }

    private Calendar displayingCalendar;;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*// passing getActivity() to allow communication with navigation activity class
        navigationActivityViewModel = ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);

        // set center text view visible to true
        navigationActivityViewModel.setCenterTextViewVisible(true);
        navigationActivityViewModel.setToolbarTitle("Schedule");
        navigationActivityViewModel.setToolbarSubtitle(null);

        setObservers(); */

        // passing getActivity() to allow communication with navigation activity class
        scheduleViewModel = ViewModelProviders.of(getActivity()).get(ScheduleViewModel.class);
        navigationActivityViewModel = ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);

        // set center text view visible to true
        navigationActivityViewModel.setCenterTextViewVisible(true);
        navigationActivityViewModel.setToolbarTitle("Schedule");
        navigationActivityViewModel.setToolbarSubtitle(null);

        setObservers();

    }


    private void setObservers() {
        displayingCalendar = Calendar.getInstance();

        scheduleViewModel.getEvents(displayingCalendar.get(Calendar.YEAR), displayingCalendar.get(Calendar.MONTH) + 1).observe(this, new Observer<List<CalendarEvent>>() {
            @Override
            public void onChanged(List<CalendarEvent> user) {
                TreeMap<Integer, CalendarEvent> dateAndEventData = new TreeMap<>();

                // saving off days and event dates in seperate lists
                List<CalendarDay> daysOff = new ArrayList<>();
                List<CalendarDay> eventDates = new ArrayList<>();

                // for each date in user events
                for (CalendarEvent date : user) {
                    // get an instance of the current calendar


                    // if the curernt date is an off day
                    if (scheduleViewModel.isOffDay(date)) {
                        // add it to the off days
                        daysOff.add(date.getEventDate());
                    } else { // otherwise it is an event day
                        eventDates.add(date.getEventDate());
                    }

                    // only display in recycler view if the month and year of the event match the month and day
                    // of the calender currently being displayed
                    /*if (date.getEventDate().getMonth() == CalendarUtils.getMonth(displayingCalendar)+1
                            && date.getEventDate().getYear() == CalendarUtils.getYear(displayingCalendar)) {*/
                        // add the date and CalendarEvent (with all its data specific to date) into TreeMap
                        dateAndEventData.put(date.getEventDate().getDay(), date);
                    //}


                }

                // put mark under all the off days
                markOffDaysOnCalendar(daysOff);

                // put mark under all event days
                markEventDaysOnCalendar(eventDates);

                // update the recyclerview
                updateRecyclerView(dateAndEventData);
            }
        });

        scheduleCalenderView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                displayingCalendar.setTime(date.getDate());
                scheduleViewModel.fetchEvents(date.getYear(), date.getMonth()+1);
            }
        });
    }

    private void initializeRecyclerView(View view) {

        myScheduleRecyclerViewAdapter = new MyScheduleRecyclerViewAdapter(getContext(), null);
        myScheduleRecyclerView = view.findViewById(R.id.mySchedule_recyclerView);
        myScheduleRecyclerView.setAdapter(myScheduleRecyclerViewAdapter);
        myScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(myScheduleRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        myScheduleRecyclerView.addItemDecoration(dividerItemDecoration);

    }

    private void markOffDaysOnCalendar(List<CalendarDay> daysOff) {
        scheduleCalenderView.addDecorator(new CalendarDecorator(daysOff, ContextCompat.getColor(getActivity(), OffDay.MARKER_COLOR)));
    }

    private void updateRecyclerView(TreeMap<Integer, CalendarEvent> dateAndEventData) {
        myScheduleRecyclerViewAdapter.updateSchedule(dateAndEventData);
    }

    private void markEventDaysOnCalendar(List<CalendarDay> eventDates) {
        scheduleCalenderView.addDecorator(new CalendarDecorator(eventDates, ContextCompat.getColor(getActivity(), OtherEvent.MARKER_COLOR)));
    }


}

