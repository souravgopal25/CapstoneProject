package com.example.capstoneproject.networking;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.capstoneproject.model.Bill;
import com.example.capstoneproject.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseData extends AsyncTask<Order,Void,Boolean> {
    private static final String TAG=FirebaseData.class.getSimpleName();


    @Override
    protected Boolean doInBackground(Order... orders) {
        Order order=orders[0];
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("USER").child("ORDER");
        return myRef.child(order.getZip()).push().setValue(order).isSuccessful();
    }
}
