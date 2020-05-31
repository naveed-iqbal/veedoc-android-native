package com.veemed.veedoc.models.event;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.ParseException;
import java.util.Date;

public class OffDay extends CalendarEvent {

    public final static int MARKER_COLOR = android.R.color.holo_red_dark;

    public OffDay(CalendarDay eventDate) {
        super(eventDate, "OFF");
    }

    public OffDay(OffDayModel offDayModel) {
        // 2020-05-20T00:00:00
        try {
            Date date = DATE_FORMAT.parse(offDayModel.getOffDay());
            /*Calendar c = Calendar.getInstance();
            c.setTime(date);*/
            CalendarDay calendarDay = CalendarDay.from(date);

            eventDate = calendarDay;
            description = "OFF";
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
