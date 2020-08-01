package com.example.capstoneproject.view;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.adapter.MainActivityAdapter;
import com.example.capstoneproject.utils.ListItemClickListener;
import com.example.capstoneproject.viewModel.MainActivityViewModel;
import com.example.dataforapp.Category;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.capstoneproject.utils.Constant.CATEGORY;


public class MainActivity extends AppCompatActivity implements ListItemClickListener {

    @BindView(R.id.toolbar_container)
    AppBarLayout toolbarContainer;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    List<Category> mList;
    MainActivityAdapter mainActivityAdapter;
    public static final String TAG = MainActivity.class.getSimpleName();
    MainActivityViewModel mainActivityViewModel;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mList = new ArrayList<>();
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        Context context;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setHasFixedSize(true);
        mainActivityAdapter = new MainActivityAdapter(this, this);
        recycler.setAdapter(mainActivityAdapter);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getmLivedata().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                mainActivityAdapter.setmList(categories);
                mList.addAll(categories);
            }
        });
    }



    @Override
    public void onListItemClick(int clickedIndex) {
        Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
        try {
            mList.get(clickedIndex);
            Log.e(TAG, mList.get(clickedIndex).toString());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        intent.putExtra(CATEGORY, mList.get(clickedIndex).getItemName());
        //TODO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(new Explode());
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(intent);
        }


    }

}