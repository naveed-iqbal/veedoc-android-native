package com.veemed.veedoc.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProfileManagementViewModel extends NavigationActivityViewModel {
    private MutableLiveData<String> licensedStateText = new MutableLiveData<>();

    public ProfileManagementViewModel() {
        super();
    }

    public int findCurrentStateIndex(List<String> statesList) {
        for (int i = 0; i < statesList.size(); i++){
            // if it is the persons current state
            if (statesList.get(i).equals(userRepo.getUserAPIResponse().getValue().getReturnObject().getLocation().getState())){
                return i;
            }
        }
        return -1; // error
    }

    public void setLicensedStateText() {
        if (getLicensedStatesSize() > 1) {
            licensedStateText.setValue(getFirstLicensedState() + " +  (" + (getLicensedStatesSize() - 1) + " more)");
        } else  { // if 0 set text to null, if 1 set to that single state name (getFirstLicensedState returns null if there are no states)
            licensedStateText.setValue(getFirstLicensedState());
        }
    }

    public LiveData<String> getLicesnedStateText(){
        return licensedStateText;
    }


    public LiveData<LinkedHashMap<String, String>> getLicensedStatesAndNumbers() {
        return userRepo.getLicensedStatesAndNumbers();
    }

    public void removeLicensedStateAndNumber(String state) {
        userRepo.removeLicensedStateAndNumber(state);
    }

    public void updateUserSpecialistInfoLicensedStateAndNumber(){
        userRepo.updateUserSpecialistInfoLicensedStateAndNumber();
    }


    public void addLicensedStateStateAndNumber(String state, String number) {
        userRepo.addLicensedStateStateAndNumber(state, number);
    }

    public boolean notAllLicenseNumbersEntered() {
        // TODO: CHECK THIS
        String[] states = new String[getLicensedStatesSize()];
        getLicensedStatesAndNumbers().getValue().values().toArray(states);
        for (int i = 0; i < states.length; i++){
            if (states[i].equals("")){

            }return true;
        }
        return false;
    }

    public int getLicensedStatesSize() {
        return getLicensedStatesAndNumbers().getValue().size();
    }

    public String getFirstLicensedState() {
        // TODO: CHECK THIS
        String[] states = new String[getLicensedStatesSize()];
        getLicensedStatesAndNumbers().getValue().keySet().toArray(states);
        //String[] states = (String[]) getLicensedStatesAndNumbers().getValue().keySet().toArray();
        if (getLicensedStatesSize() > 0) {
            return states[0];
        } else{
            return null;
        }
    }

    public String getStateWithoutLicenseNumber() {
        for (Map.Entry<String, String> element : getLicensedStatesAndNumbers().getValue().entrySet()){
            if (element.getValue().equals("")){
                return element.getKey();
            }
        }
        return "ERROR";
    }

    public void resetSpecialistInformationStateData() {
        userRepo.resetSpecialistInformationStateData();
    }

    public void updateUser(String fullName, String email, String mobileNumber, String zipCode, String address,
                           String deaNumber, int npiNumber, String specialityName, String practiceGroup,
                           String state, String country, String city, String otherAddress) {
         super.updateUser(fullName, email, mobileNumber, zipCode, address, deaNumber, npiNumber, specialityName, practiceGroup
                        , state, country, city, otherAddress);
         updateUserSpecialistInfoLicensedStateAndNumber();
         resetSpecialistInformationStateData();
    }
}
