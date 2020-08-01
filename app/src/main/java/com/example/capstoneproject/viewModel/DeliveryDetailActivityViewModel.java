package com.example.capstoneproject.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.capstoneproject.repository.CartRepo;

public class DeliveryDetailActivityViewModel extends AndroidViewModel {
    CartRepo cartRepo;
    public DeliveryDetailActivityViewModel(@NonNull Application application) {
        super(application);
        cartRepo=new CartRepo(application);
    }
     public void deleteAll(){
        cartRepo.deleteall();
    }
}
