package com.veemed.veedoc.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.veemed.veedoc.adapters.CalendarDecorator;
import com.veemed.veedoc.models.event.CalendarEvent;
import com.veemed.veedoc.models.event.Details;
import com.veemed.veedoc.models.event.OffDay;
import com.veemed.veedoc.models.event.OffDayModel;
import com.veemed.veedoc.models.event.OtherEvent;
import com.veemed.veedoc.models.event.ScheduledEvent;
import com.veemed.veedoc.repositories.VeeDocRepository;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ScheduleViewModel extends NavigationActivityViewModel {

    private List<CalendarDay> tempUserOffDay = new ArrayList<>();
    private HashMap<CalendarDay, CalendarDecorator> datesToDecoratorsMap = new HashMap<>();
    private List<CalendarDay> offDaysToRemove = new ArrayList<>();

    private MutableLiveData<List<CalendarEvent>> offDays = new MutableLiveData<>();
    private MutableLiveData<List<CalendarEvent>> events = new MutableLiveData<>();
    List<ScheduledEvent> scheduledEvents;
    List<OffDayModel> offDaysList;


    public ScheduleViewModel() {
        super();
    }

    public void addTempUserOffDay(CalendarDay date) {
        tempUserOffDay.add(date);
    }

    public void setUserOffDays() {
        removeOffDaysFromUser();
        addOffDaysToUser();
        clearTempOffDays();
    }



    public MutableLiveData<List<CalendarEvent>> getEvents(int year, int month) {
        fetchEvents(year, month);
        return events;
    }

    public void fetchEvents(int year, int month) {
        VeeDocRepository.getInstance().getEvents(year, month, new RetrofitCallbackListener<List<ScheduledEvent>>() {
            @Override
            public void onResponse(Call<List<ScheduledEvent>> call, Response<List<ScheduledEvent>> response, int requestID) {
                if(response.isSuccessful()) {
                    scheduledEvents = response.body();
                    fetchOffDays(year, month);
                }
            }

            @Override
            public void onFailure(Call<List<ScheduledEvent>> call, Throwable t, int requestID) {

            }
        }, 0);
    }

    private void fetchOffDays(int year, int month) {
        VeeDocRepository.getInstance().getOffDays(year, month, new RetrofitCallbackListener<List<OffDayModel>>() {
            @Override
            public void onResponse(Call<List<OffDayModel>> call, Response<List<OffDayModel>> response, int requestID) {
                if(response.isSuccessful()) {
                    offDaysList = response.body();

                    List<CalendarEvent> cEvents = new ArrayList<>();
                    // add all events
                    if(scheduledEvents != null){
                        for (ScheduledEvent e : scheduledEvents) {
                            List<Details> details = e.getDetails();
                            for (Details d : details) {
                                cEvents.add(new OtherEvent(d));
                            }
                        }
                    }

                    // add all offDays
                    for(OffDayModel e: offDaysList) {
                        cEvents.add(new OffDay(e));
                    }

                    events.setValue(cEvents);

                }
            }

            @Override
            public void onFailure(Call<List<OffDayModel>> call, Throwable t, int requestID) {

            }
        }, 0);
    }

    public MutableLiveData<List<CalendarEvent>> getOffDays(int year, int month) {
        fetchOffDaysOnly(year, month);
        return offDays;
    }

    private void fetchOffDaysOnly(int year, int month) {
        VeeDocRepository.getInstance().getOffDays(year, month, new RetrofitCallbackListener<List<OffDayModel>>() {
            @Override
            public void onResponse(Call<List<OffDayModel>> call, Response<List<OffDayModel>> response, int requestID) {
                if(response.isSuccessful()) {
                    offDaysList = response.body();

                    List<CalendarEvent> cEvents = new ArrayList<>();
                    // add all offDays
                    for(OffDayModel e: offDaysList) {
                        cEvents.add(new OffDay(e));
                    }

                    offDays.setValue(cEvents);

                }
            }

            @Override
            public void onFailure(Call<List<OffDayModel>> call, Throwable t, int requestID) {

            }
        }, 0);
    }
    private void removeOffDaysFromUser() {
        // for each day you need to remove
        for (CalendarDay date : offDaysToRemove){
            Iterator<CalendarEvent> iterator = getUserEvents().iterator();
            while (iterator.hasNext()){
                // if the current calendar events underlying calendar day is equivalent to the calendar day of this
                // remove it if this date is not also in the temporary storage of user off days (in case we click same date twice and it is now in dates we want to add)
                if (iterator.next().getEventDate().equals(date)){
                    iterator.remove();
                }
            }
        }
    }

    private void addOffDaysToUser() {
        // for each day you need to add
        for (CalendarDay date : tempUserOffDay){
            // only add if not in user events
            if (!isAlreadyInUserEvents(date)) {
                // add an off day
                //userRepo.addUserEvent(new CalendarEvent(date, "OFF"));
                userRepo.addUserEvent(new OffDay(date));
            }
        }
    }

    public boolean isDateAlreadyMarked(CalendarDay date) {
        // return if the temporarily (but not saved) dates contain this date
        return tempUserOffDay.contains(date);
    }

    private boolean isAlreadyInUserEvents(CalendarDay date) {
        for (CalendarEvent calendarEvent : getUserEvents()){
            // if the calendarEvent's underlaying Calendar day is equivalent to te date
            if (calendarEvent.getEventDate().equals(date)){
                return true;
            }
        }
        return false;
    }

    public void removeTempUserOffDay(CalendarDay date) {
        tempUserOffDay.remove(date);
        offDaysToRemove.add(date);
    }

    public void addDecoratorMapping(CalendarDay date, CalendarDecorator decorator) {
        datesToDecoratorsMap.put(date, decorator);
    }

    public DayViewDecorator getDecoratorForDate(CalendarDay date) {
        return datesToDecoratorsMap.get(date);
    }

    public List<CalendarEvent> getUserEvents() {
        return userRepo.getUserEvents();
    }

    public void clearTempOffDays() {
        tempUserOffDay.clear();
        offDaysToRemove.clear();

    }

    public boolean isEventDay(CalendarDay date) {
        for (CalendarEvent calendarEvent : getUserEvents()){
            if (calendarEvent.getEventDate().equals(date)){
                return !(calendarEvent instanceof OffDay);
            }
        }

        return false; // there were no dates in the user events that matched. That means the date we are clicking must be an off day, as we can only mark off days here
    }

    public boolean isOffDay(CalendarEvent calendarEvent){
        return calendarEvent instanceof OffDay;
    }
}
