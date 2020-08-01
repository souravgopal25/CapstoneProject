package com.example.capstoneproject.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.capstoneproject.model.CartItem;

import com.example.capstoneproject.room.AddtoCartDao;
import com.example.capstoneproject.room.CartDatabase;

import com.example.dataforapp.CategoryData;
import com.example.dataforapp.Item;
import com.example.dataforapp.ItemData;


import java.util.List;

public class CartRepo {
    private AddtoCartDao addtoCartDao;
    private LiveData<List<Item>> mList;

    public CartRepo(Application application) {
        CartDatabase db=CartDatabase.getDatabase(application);
        this.addtoCartDao = db.addtoCartDao();

    }
    public void insert(final CartItem cartItem){
       CartDatabase.databaseWriteExecutor.execute(new Runnable() {
           @Override
           public void run() {
               addtoCartDao.insert(cartItem);
           }
       });


    }
    public LiveData<List<Item>> getItemList(Context context,String category) {
        final MutableLiveData<List<Item>> data=new MutableLiveData<>();
        ItemData itemData=new ItemData(category);
        data.setValue(itemData.getItemList());
        return data;
    }
}
