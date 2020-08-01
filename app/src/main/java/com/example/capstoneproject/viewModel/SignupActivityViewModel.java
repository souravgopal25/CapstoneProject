package com.example.capstoneproject.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.capstoneproject.model.User;
import com.example.capstoneproject.repository.FireBaseRepo;

public class SignupActivityViewModel extends AndroidViewModel {
    FireBaseRepo fireBaseRepo;
    public SignupActivityViewModel(@NonNull Application application) {
        super(application);
        fireBaseRepo=new FireBaseRepo(application);

    }
    public void UploadUsers(User user){
        fireBaseRepo.uploadProfile(user);
    }
}
