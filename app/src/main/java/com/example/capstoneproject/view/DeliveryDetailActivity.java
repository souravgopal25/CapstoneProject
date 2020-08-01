package com.example.capstoneproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstoneproject.R;
import com.example.capstoneproject.model.Bill;
import com.example.capstoneproject.model.Order;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeliveryDetailActivity extends AppCompatActivity {

    @BindView(R.id.fullName)
    EditText fullName;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.landmark)
    EditText landmark;
    @BindView(R.id.zip)
    EditText zip;
    @BindView(R.id.btnOrder)
    Button btnOrder;

    String sName,sAddress,sLandmark,sZip;
    Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_detail);
        Intent intent=getIntent();
        order=intent.getParcelableExtra("order");
        ButterKnife.bind(this);
        getData();

    }

    private void getData() {
        sName=fullName.getText().toString();
        sAddress=address.getText().toString();
        sLandmark=landmark.getText().toString();
        sZip=zip.getText().toString();
    }

    @OnClick(R.id.btnOrder)
    public void onViewClicked() {
        Bill bill=new Bill(order,sName,sAddress,sLandmark,sZip);

    }
}