package com.example.capstoneproject.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.capstoneproject.model.CartItem;
import com.example.capstoneproject.room.AddtoCartDao;
import com.example.capstoneproject.room.CartDatabase;

import java.util.List;

public class BillingRepo {
    private AddtoCartDao addtoCartDao;
    private LiveData<List<CartItem>> mlist;

    public BillingRepo(Application application) {
       CartDatabase cartDatabase= CartDatabase.getDatabase(application);
       addtoCartDao=cartDatabase.addtoCartDao();
       mlist=addtoCartDao.getCartItems();
    }

    public LiveData<List<CartItem>> getMlist() {
        return mlist;
    }
    public void remove(final CartItem cartItem){
        CartDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                addtoCartDao.delete(cartItem);
            }
        });
    }
}
