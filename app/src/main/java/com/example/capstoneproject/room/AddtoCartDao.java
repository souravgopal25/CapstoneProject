package com.example.capstoneproject.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.capstoneproject.model.CartItem;

import java.util.List;
@Dao
public interface AddtoCartDao {
    @Insert
    void insert(CartItem cartItem);
    @Delete
    void delete(CartItem cartItem);

    @Query("SELECT * FROM  cart ORDER BY id")
    LiveData<List<CartItem>> getCartItems();
}
