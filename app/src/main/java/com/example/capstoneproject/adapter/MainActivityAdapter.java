package com.example.capstoneproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstoneproject.R;

import com.example.capstoneproject.utils.ListItemClickListener;
import com.example.dataforapp.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.MovieViewHolder> {
    public static final String TAG=MainActivityAdapter.class.getSimpleName();
    private Context context;
    private List<Category> mList;
    final private ListItemClickListener mOnClickListener;
    public MainActivityAdapter(Context context, ListItemClickListener mOnClickListener) {
        this.context = context;
        this.mOnClickListener = mOnClickListener;
        this.mList = new ArrayList<>();
    }

    public void setmList(List<Category> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater inflater=LayoutInflater.from(context);
       View view=inflater.inflate(R.layout.item_card_main, parent, false);
       return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Category object=mList.get(position);
        Glide.with(context).load(object.getImageUrl()).into(holder.imageView);
        holder.imageView.setContentDescription(object.getDescription());
        holder.textView.setText(object.getItemName());
        holder.textView1.setText(object.getDescription());

    }

    @Override
    public int getItemCount() {
        try {
            return mList.size();
        }catch (Exception e){
            return  0;
        }

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView,textView1;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageMain);
            textView=itemView.findViewById(R.id.name);
            textView1=itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int clickedposition=getAdapterPosition();
            mOnClickListener.onListItemClick(clickedposition);


        }
    }
}
