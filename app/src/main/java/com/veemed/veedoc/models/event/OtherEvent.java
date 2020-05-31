package com.veemed.veedoc.models.event;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.veemed.veedoc.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OtherEvent extends CalendarEvent {

    public final static int MARKER_COLOR = R.color.colorLightBlueTheme;
    private String timeInterval;

    public OtherEvent(CalendarDay eventDate, String description, String timeInterval) {
        super(eventDate, description);
        this.timeInterval = timeInterval;

    }


    public OtherEvent(Details details) {
        super();
        try {
            Date date = DATE_FORMAT.parse(details.getShiftStartTime());
            String description = details.getFacilityName();

            eventDate = CalendarDay.from(date);
            this.description = description;
            timeInterval = details.getShiftEndTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public static List<OtherEvent> parseOtherEvents(ScheduledEvent event) {
        List<OtherEvent> events = new ArrayList<>();

        for(Details d: event.getDetails()) {
            events.add(new OtherEvent(d));
        }

        return events;
    }



    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

}
