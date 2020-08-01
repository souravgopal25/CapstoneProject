package com.example.capstoneproject.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.capstoneproject.model.CartItem;
import com.example.capstoneproject.model.Order;
import com.example.capstoneproject.repository.BillingRepo;
import com.example.capstoneproject.repository.FireBaseRepo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class CartActivityViewModel extends AndroidViewModel {

    private BillingRepo billingRepo;
    private FireBaseRepo fireBaseRepo;
    private LiveData<List<CartItem>> mList;
    private FirebaseAuth mAuth;
    public CartActivityViewModel(@NonNull Application application) {
        super(application);
        billingRepo=new BillingRepo(application);
        fireBaseRepo=new FireBaseRepo(application);
        mList=billingRepo.getMlist();
        mAuth = FirebaseAuth.getInstance();
    }
    public void remove(CartItem cartItem){

        billingRepo.remove(cartItem);
    }

    public LiveData<List<CartItem>> getmList() {
        mList=billingRepo.getMlist();
        return mList;
    }
    public  void upload(Order order){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        fireBaseRepo.uploadOrder(order,uid);
    }


}
