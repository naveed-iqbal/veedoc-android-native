package com.veemed.veedoc.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.veemed.veedoc.BR;
import com.veemed.veedoc.models.event.CalendarEvent;

import java.util.ArrayList;
import java.util.List;

public class User extends BaseObservable {
    private String email;
    private String mobileNumber;
    private String lastName;
    private String firstName;
    private String credentials;
    private String title;
    private String password;
    private Location location;
    private SpecialistInformation specialistInformation;
    private String fullName;
    private List<CalendarEvent> events;

    // TODO: No longer need password attribute
    public User(String email, String mobileNumber, String lastName, String firstName, String credentials, String title, String password, Location location, SpecialistInformation specialistInformation, List<CalendarEvent> events) {
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.credentials = credentials;
        this.title = title;
        this.password = password;
        this.location = location;
        this.specialistInformation = specialistInformation;
        this.fullName = firstName + " " + lastName;
        this.events = events;

    }

    public User(String email, String mobileNumber, String lastName, String firstName, String credentials, String title, String password) {
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.credentials = credentials;
        this.title = title;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String mobileNumber, String lastName, String firstName, String credentials, String title, Location location, SpecialistInformation specialistInformation, List<CalendarEvent> events) {
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.credentials = credentials;
        this.title = title;
        this.password = password;
        this.location = location;
        this.specialistInformation = specialistInformation;
        this.fullName = firstName + " " + lastName;
        this.events = events;

    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        notifyPropertyChanged(BR.mobileNumber);
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
        notifyPropertyChanged(BR.credentials);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        notifyPropertyChanged(BR.location);
    }

    @Bindable
    public SpecialistInformation getSpecialistInformation() {
        return specialistInformation;
    }

    public void setSpecialistInformation(SpecialistInformation specialistInformation) {
        this.specialistInformation = specialistInformation;
        notifyPropertyChanged(BR.specialistInformation);
    }

    @Bindable
    public String getFullName(){
        return fullName;
    }

    public String getFirstInitial(){
        return getFirstName().substring(0,1);
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
        if (fullName.split(" ").length == 2) {
            setFirstName(fullName.split(" ")[0]);
            setLastName(fullName.split(" ")[1]);
        }
        notifyPropertyChanged(BR.fullName);
    }

    public List<CalendarEvent> getEvents() {
        return events;
    }

    public void setEvents(List<CalendarEvent> events) {
        this.events = new ArrayList<>(events);
    }

    public void addEvent(CalendarEvent date) {
        this.events.add(date);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", credentials='" + credentials + '\'' +
                ", title='" + title + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
