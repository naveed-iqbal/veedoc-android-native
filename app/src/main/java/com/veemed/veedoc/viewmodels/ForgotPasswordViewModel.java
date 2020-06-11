package com.veemed.veedoc.viewmodels;

import androidx.lifecycle.ViewModel;

import com.veemed.veedoc.utils.Utility;
import com.veemed.veedoc.repositories.VeeDocRepository;

public class ForgotPasswordViewModel extends ViewModel {
    private VeeDocRepository userRepo;

    public ForgotPasswordViewModel() {
        userRepo = VeeDocRepository.getInstance();
    }

    public String getErrorToast(String email){
        if (email.isEmpty()){
            return "Failed\nEmail is required";
        } else if (!Utility.isPossibleEmail(email)){
            return "Failed\nEmail is invalid";
        } else if (!accountWithEmailExists(email)){
            return "Failed\nEmail does not exist!";
        }
        return "ERROR";
    }

    public boolean accountWithEmailExists(String email){
        String encodedEmail = Utility.encodeText(email);
        return userRepo.accountWithEmailExists(encodedEmail);
    }

    public void updatePassword(){
        userRepo.updatePassword();
    }

    public boolean cannotResetPassword(String emailEntered) {
        return emailEntered.trim().isEmpty() || !Utility.isPossibleEmail(emailEntered) || !accountWithEmailExists(emailEntered);
    }
}
