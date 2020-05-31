package com.veemed.veedoc.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.veemed.veedoc.utils.ReturnResponse;
import com.veemed.veedoc.utils.Utility;
import com.veemed.veedoc.models.User;
import com.veemed.veedoc.repositories.VeeDocUserRepository;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;
import com.veemed.veedoc.webservices.UserAPIResponse;

public class SignUpActivityViewModel extends ViewModel {
    private VeeDocUserRepository userRepo;
    private String email, mobileNumber, credentials, title, password, confirmPassword, lastName, firstName;
    public MutableLiveData<ReturnResponse<String>> signUpInitResponse = new MutableLiveData<>();
    private static final int INIT_SIGN_UP_REQUEST = 1;
    public SignUpActivityViewModel() {
        userRepo = VeeDocUserRepository.getInstance();
    }


    public boolean infoCorrectToSignUp() {
        return password.equals(confirmPassword) && Utility.isPossibleEmail(email) && !accountWithEmailExists(email);
    }

    public boolean accountWithEmailExists(String email){
        String encodedEmail = Utility.encodeText(email);
        return userRepo.accountWithEmailExists(encodedEmail);
    }

    public void initRegistration() {
        UserAPIResponse user = new UserAPIResponse();
        user.setEmail(email);
        user.setMobileNumber(mobileNumber);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setCredentials(credentials);
        user.setTitle(title);
        user.setPassword(password);
        signUpInitResponse = userRepo.initRegistration(user);
    }

    public boolean validateSignUpInit() {
        boolean valid = true;
        if(email.isEmpty()) {
            valid = false;
        } else if(password.isEmpty()) {
            valid = false;
        } else if(confirmPassword.isEmpty()) {
            valid = false;
        } else if(firstName.isEmpty()) {
            valid = false;
        } else if(lastName.isEmpty()) {
            valid = false;
        } else if(title.isEmpty()) {
            valid = false;
        } else if(credentials.isEmpty()) {
            valid = false;
        } else if(mobileNumber.isEmpty()) {
            valid = false;
        }

        if(!password.equals(confirmPassword)) {
            valid = false;
        }
        return valid;
    }

    public User saveUser(String email){
        String encodedEmail = Utility.encodeText(email);
        // return userRepo.accountWithEmailExists(encodedEmail);
        return  null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName(){
        return getFirstName() + getLastName();
    }


}
