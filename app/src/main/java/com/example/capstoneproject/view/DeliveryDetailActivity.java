package com.example.capstoneproject.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.capstoneproject.R;
import com.example.capstoneproject.model.Bill;
import com.example.capstoneproject.model.Order;
import com.example.capstoneproject.networking.FirebaseData;
import com.example.capstoneproject.viewModel.DeliveryDetailActivityViewModel;

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
    private String TAG=DeliveryDetailActivity.class.getSimpleName();
    String sName,sAddress,sLandmark,sZip;
    Order order;
    DeliveryDetailActivityViewModel deliveryDetailActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_detail);
        Intent intent=getIntent();
        order=intent.getParcelableExtra("order");
        ButterKnife.bind(this);
        deliveryDetailActivityViewModel= ViewModelProviders.of(this).get(DeliveryDetailActivityViewModel.class);


    }

    private void getData() {
        sName=fullName.getText().toString();
        sAddress=address.getText().toString();
        sLandmark=landmark.getText().toString();
        sZip=zip.getText().toString();
    }

    @OnClick(R.id.btnOrder)
    public void onViewClicked() {
        getData();
       order.setAddress(sAddress);
       order.setName(sName);
       order.setLandmarks(sLandmark);
       order.setZip(sZip);
        Log.e(TAG,order.toString()+order.getAddress()+"\n"+order.getZip());
        new FirebaseData().execute(order);
        Toast.makeText(this, getResources().getString(R.string.orderplaced), Toast.LENGTH_SHORT).show();
        deliveryDetailActivityViewModel.deleteAll();
        Intent intent=new Intent(DeliveryDetailActivity.this,MainActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(new Explode());
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(intent);
        }
        startActivity(intent);

    }
}