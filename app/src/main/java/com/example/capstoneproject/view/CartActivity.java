package com.example.capstoneproject.view;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.BuildConfig;
import com.example.capstoneproject.R;
import com.example.capstoneproject.adapter.CartAdapter;
import com.example.capstoneproject.model.CartItem;
import com.example.capstoneproject.model.Order;
import com.example.capstoneproject.utils.Constant;
import com.example.capstoneproject.utils.ListItemClickListener;
import com.example.capstoneproject.viewModel.CartActivityViewModel;
import com.example.capstoneproject.widget.OrderWidget;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;

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
    private static int orderno = 0;
    List<CartItem> mList;
    CartAdapter cartAdapter;
    public static final String TAG = CartActivity.class.getSimpleName();
    CartActivityViewModel cartActivityViewModel;
    float mtotal = 0;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private SharedPreferences sharedPreferences;

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

        sharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        cartActivityViewModel = ViewModelProviders.of(this).get(CartActivityViewModel.class);
        cartActivityViewModel.getmList().observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                mList.clear();
                mList.addAll(cartItems);
                Toast.makeText(CartActivity.this, "NO of items in Cart" + cartItems.size(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "No of items in Cart " + cartItems.size());
                cartAdapter.setmList(mList);
                mtotal = 0;
                float st=0;
                for (CartItem obj : mList
                ) {
                    float price = obj.getRate() * obj.getQty();
                    st += price;
                    Log.e(TAG,"OBJECT NAME :"+obj.getName()+"\n"+obj.getPrice());

                }
                mtotal=st;
                total.setText("$" + st);
                Log.e(TAG,"TOTAL = "+mtotal);
            }
        });

    }

    @Override
    public void onListItemClick(int clickedIndex) {
        cartActivityViewModel.remove(mList.get(clickedIndex));

    }

    @OnClick(R.id.btnnext)
    public void onViewClicked() {
        widget();
        orderno++;
        Order order = new Order(mList, mtotal, orderno);
        cartActivityViewModel.upload(order);
        Intent intent = new Intent(CartActivity.this, DeliveryDetailActivity.class);
        intent.putExtra("order", order);
        startActivity(intent);
    }

    public void widget() {


        boolean isRecipeInWidget = (sharedPreferences.getInt(Constant.PREFERENCES_ID, -1) == 1);

        // If recipe already in widget, remove it
        if (isRecipeInWidget) {
            sharedPreferences.edit()
                    .remove(Constant.PREFERENCES_ID)
                    .remove(Constant.PREFERENCES_WIDGET_CONTENT)
                    .apply();


           // Snackbar.make(coordinatorLayout, "Order Removed From Widget", Snackbar.LENGTH_SHORT).show();
        }
        // if recipe not in widget, then add it

        sharedPreferences
                    .edit()
                    .putInt(Constant.PREFERENCES_ID, 1)
                    .putString(Constant.PREFERENCES_WIDGET_CONTENT, orderDetail())
                    .apply();


        Snackbar.make(coordinatorLayout, "Order Added to Widget", Snackbar.LENGTH_SHORT).show();

        // Put changes on the Widget
        ComponentName provider = new ComponentName(this, OrderWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] ids = appWidgetManager.getAppWidgetIds(provider);
        OrderWidget orderWidget = new OrderWidget();
        orderWidget.onUpdate(this,appWidgetManager,ids);


    }

    private String orderDetail() {
       String result="";
       for (CartItem cartItem:mList){
           String str= cartItem.getName()+"   "+cartItem.getQty()+" * $"+cartItem.getRate()+" = "+cartItem.getPrice()+" \n";
           result+=str;
       }
       return result;
    }
}