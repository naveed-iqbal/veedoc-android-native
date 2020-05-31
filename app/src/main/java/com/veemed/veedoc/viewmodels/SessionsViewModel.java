package com.veemed.veedoc.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.veemed.veedoc.models.Session;
import com.veemed.veedoc.utils.ReturnResponse;

import java.util.Calendar;
import java.util.List;

public class SessionsViewModel extends NavigationActivityViewModel {

    public SessionsViewModel() {
        super();
    }

    public MutableLiveData<ReturnResponse<List<Session>>> lastYearSessionLiveData;
    public MutableLiveData<ReturnResponse<List<Session>>> lastWeekSessionLiveData;
    public MutableLiveData<ReturnResponse<List<Session>>> lastMonthSessionLiveData;

    public MutableLiveData<ReturnResponse<List<Session>>> getLastYearSessions(int param) {
        Calendar c = Calendar.getInstance();
        lastYearSessionLiveData = userRepo.getSessions(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), param);
        return lastYearSessionLiveData;
    }

    @Deprecated
    public MutableLiveData<ReturnResponse<List<Session>>> getLastWeekSessions() {
        Calendar c = Calendar.getInstance();
        lastWeekSessionLiveData = userRepo.getSessions(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 365);
        return lastWeekSessionLiveData;
    }

    @Deprecated
    public MutableLiveData<ReturnResponse<List<Session>>> getLastMonthSessions() {
        Calendar c = Calendar.getInstance();
        lastMonthSessionLiveData = userRepo.getSessions(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 365);
        return lastMonthSessionLiveData;
    }
}
