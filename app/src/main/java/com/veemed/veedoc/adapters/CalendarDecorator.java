package com.veemed.veedoc.adapters;

import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.UnderlineSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.veemed.veedoc.R;

import java.util.Calendar;
import java.util.List;

/*
CREDIT TO: https://github.com/prolificinteractive/material-calendarview/wiki/Decorators
 */

public class CalendarDecorator implements DayViewDecorator {
    private List<CalendarDay> offDays;
    private int color;
    private CalendarDay day;

    public CalendarDecorator(List<CalendarDay> offDays, int color) {
        this.offDays = offDays;
        this.color = color;
        this.day = null;
    }

    public CalendarDecorator(CalendarDay day, int color){
        this.day = day;
        this.color = color;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (offDays == null){
            return this.day.equals(day);
        } else {
            return offDays.contains(day);
        }
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(10, color));

    }
}
