package com.example.capstoneproject.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.dataforapp.Category;
import com.example.dataforapp.CategoryData;

import java.util.List;

public class MainActivityRepo {

    public LiveData<List<Category>> getCategoryResult(Context context) {
    final MutableLiveData<List<Category>> data=new MutableLiveData<>();
    CategoryData categoryData=new CategoryData();
    data.setValue(categoryData.getListCategories());
    return data;
    }
}
