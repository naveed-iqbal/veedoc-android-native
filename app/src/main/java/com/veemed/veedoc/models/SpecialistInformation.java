package com.veemed.veedoc.models;

import com.veemed.veedoc.utils.Utility;

import java.util.LinkedHashMap;

public class SpecialistInformation {
    private String deaNumber;
    private int npiNumber;
    private LinkedHashMap<String, String> statesAndNumbers;
    private String specialityName;
    private String practiceGroup;
    private String statesAndNumbersText;

    public SpecialistInformation(String deaNumber, int npiNumber, LinkedHashMap<String, String> statesAndNumbers, String specialityName, String practiceGroup) {
        this.deaNumber = deaNumber;
        this.npiNumber = npiNumber;

        // deep copy the linkedHashMap
        this.statesAndNumbers = new LinkedHashMap<String, String>();
        Utility.deepCopyStatesAndNumbersMap(statesAndNumbers, this.statesAndNumbers);

        setStatesAndNumbersText();
        this.specialityName = specialityName;
        this.practiceGroup = practiceGroup;
    }

    public SpecialistInformation() {

    }

    public String getDeaNumber() {
        return deaNumber;
    }

    public void setDeaNumber(String deaNumber) {
        this.deaNumber = deaNumber;
    }

    public int getNpiNumber() {
        return npiNumber;
    }

    public void setNpiNumber(int npiNumber) {
        this.npiNumber = npiNumber;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public String getPracticeGroup() {
        return practiceGroup;
    }

    public void setPracticeGroup(String practiceGroup) {
        this.practiceGroup = practiceGroup;
    }

    public LinkedHashMap<String, String> getStatesAndNumbers() {
        return statesAndNumbers;
    }

    public void setStatesAndNumbers(LinkedHashMap<String, String> statesAndNumbers) {
        this.statesAndNumbers = new LinkedHashMap<>();
        // deep copy the linkedHashMap
        Utility.deepCopyStatesAndNumbersMap(statesAndNumbers, this.statesAndNumbers);
        setStatesAndNumbersText();
    }

    public String getStatesAndNumbersText() {
        return statesAndNumbersText;
    }

    public void setStatesAndNumbersText() {
        if (getLicensedStatesSize() > 1) {
            statesAndNumbersText = getFirstLicensedState() + " +  (" + (getLicensedStatesSize() - 1) + " more)";
        } else {
            statesAndNumbersText = getFirstLicensedState();
        }
    }

    private String getFirstLicensedState() {
        String[] states = new String[getLicensedStatesSize()];
        statesAndNumbers.keySet().toArray(states);
        if (states.length > 0){
            return states[0];
        } else {
            return null;
        }

    }

    private int getLicensedStatesSize() {
        return statesAndNumbers.size();
    }
}
