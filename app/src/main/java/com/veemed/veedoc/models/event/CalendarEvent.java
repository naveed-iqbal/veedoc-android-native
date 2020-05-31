package com.veemed.veedoc.models.event;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;

public abstract class CalendarEvent {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    CalendarDay eventDate;
    String description;

    public CalendarEvent() {

    }

    public CalendarEvent(CalendarDay eventDate, String description) {
        this.eventDate = eventDate;
        this.description = description;
    }


    public CalendarDay getEventDate() {
        return eventDate;
    }

    public void setEventDate(CalendarDay eventDate) {
        this.eventDate = eventDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

