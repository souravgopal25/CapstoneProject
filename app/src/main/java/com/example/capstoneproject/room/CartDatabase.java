package com.example.capstoneproject.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.capstoneproject.model.CartItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Database(entities = {CartItem.class},version = 2,exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {

    public abstract AddtoCartDao addtoCartDao();
    protected static  volatile CartDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS=4;

    public static  ExecutorService databaseWriteExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CartDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (CartItem.class){
                if (INSTANCE == null) {
                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(),
                            CartDatabase.class,"cart_db").
                            fallbackToDestructiveMigration().build();

                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

        }
    };
}
