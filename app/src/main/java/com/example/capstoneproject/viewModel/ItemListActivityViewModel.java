package com.example.capstoneproject.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.capstoneproject.model.CartItem;
import com.example.capstoneproject.repository.CartRepo;
import com.example.dataforapp.Category;
import com.example.dataforapp.Item;

import java.util.List;

public class ItemListActivityViewModel extends AndroidViewModel {
    private CartRepo cartRepo;
    private Context context;
    LiveData<List<Item>> mLivedata;
    public ItemListActivityViewModel(@NonNull Application application) {
        super(application);
        this.context=application.getApplicationContext();
        this.cartRepo=new CartRepo(application);


    }
    public void insert(CartItem cartItem){
        cartRepo.insert(cartItem);
    }
    public LiveData<List<Item>> getItemList1(String category){
        mLivedata=cartRepo.getItemList(context,category);
        return mLivedata;
    }
}
