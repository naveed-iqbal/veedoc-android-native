package com.veemed.veedoc.activities.widgets;

import android.widget.CheckBox;

public class KeyValuePair {
    private String key;
    private String value;
    private boolean checked;
    private String licensedStateNumber = "";


    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
        checked = false;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLicensedStateNumber() {
        return licensedStateNumber;
    }

    public void setLicensedStateNumber(String licensedStateNumber) {
        this.licensedStateNumber = licensedStateNumber;
    }
}