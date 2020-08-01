package com.example.capstoneproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.adapter.CartAdapter;
import com.example.capstoneproject.model.CartItem;
import com.example.capstoneproject.model.Order;
import com.example.capstoneproject.utils.ListItemClickListener;
import com.example.capstoneproject.viewModel.CartActivityViewModel;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends AppCompatActivity implements ListItemClickListener {

    @BindView(R.id.toolbar_container)
    AppBarLayout toolbarContainer;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.btnnext)
    Button btnnext;
    private static int orderno=0;
    List<CartItem> mList;
    CartAdapter cartAdapter;
    public static final String TAG = CartActivity.class.getSimpleName();
    CartActivityViewModel cartActivityViewModel;
    float mtotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        cartAdapter = new CartAdapter(this, this);
        recyclerView.setAdapter(cartAdapter);
        cartActivityViewModel = ViewModelProviders.of(this).get(CartActivityViewModel.class);
        cartActivityViewModel.getmList().observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                mList.clear();
                mList.addAll(cartItems);
                Toast.makeText(CartActivity.this, "NO of items in Cart" + cartItems.size(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "No of items in Cart " + cartItems.size());
                cartAdapter.setmList(mList);
               mtotal=0;
                for (CartItem obj : mList
                ) {
                    float price = obj.getPrice() * obj.getQty();
                    mtotal += price;

                }
                total.setText("$" + mtotal);
            }
        });

    }

    @Override
    public void onListItemClick(int clickedIndex) {
        cartActivityViewModel.remove(mList.get(clickedIndex));

    }

    @OnClick(R.id.btnnext)
    public void onViewClicked() {
        orderno++;
        Order order=new Order(mList,mtotal,orderno);
        cartActivityViewModel.upload(order);
        Intent intent=new Intent(CartActivity.this,DeliveryDetailActivity.class);
        intent.putExtra("order",order);
        startActivity(intent);
    }
}