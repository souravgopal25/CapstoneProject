package com.example.capstoneproject.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.adapter.ListItemAdapter;
import com.example.capstoneproject.model.CartItem;

import com.example.capstoneproject.viewModel.ItemListActivityViewModel;
import com.example.dataforapp.Item;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.capstoneproject.utils.Constant.CATEGORY;

public class ItemListActivity extends AppCompatActivity implements ListItemAdapter.ListItemClickListener{

    @BindView(R.id.activity_item_list_detail_toolbar)
    Toolbar activityItemListDetailToolbar;
    @BindView(R.id.activity_item_list_appbarLayout)
    AppBarLayout activityItemListAppbarLayout;
    @BindView(R.id.listItemrecycler)
    RecyclerView listItemrecycler;
    ListItemAdapter listItemAdapter;
    List<Item> mlists = new ArrayList<>();
    ItemListActivityViewModel itemListActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        String category=intent.getStringExtra(CATEGORY);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        listItemrecycler.setLayoutManager(linearLayoutManager);
        listItemrecycler.setHasFixedSize(true);
        listItemAdapter=new ListItemAdapter(this,this);
        listItemrecycler.setAdapter(listItemAdapter);
        itemListActivityViewModel=ViewModelProviders.of(this).get(ItemListActivityViewModel.class);
        itemListActivityViewModel.getItemList1(category).observe(this,new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                listItemAdapter.setmList(items);
                mlists.addAll(items);
            }
        });




    }

    public void fabButton(View view) {
        Intent intent=new Intent(ItemListActivity.this,LoginActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(new Explode());
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(intent);
        }
        startActivity(intent);
    }

    @Override
    public void onListItemClick(int clickedIndex, int subIndex) {
        //SUCCESSFULL INSERTION
        Item object=mlists.get(clickedIndex);
        float price=0;
        price=object.getPrice()*subIndex;
        CartItem cartItem=new CartItem(object.getItemName(),object.getPrice(),subIndex,price);
         itemListActivityViewModel.insert(cartItem);
        Toast.makeText(this, "SUccess", Toast.LENGTH_SHORT).show();
    }
}