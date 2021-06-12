package com.example.fristapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<ElementJournalList> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<ElementJournalList> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() { return mData.size(); }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.element_list, null);
        return new ListAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItem(List<ElementJournalList> items){ mData = items; }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView issue_id, volume, number,year,date_published,title,doi,cover;

        ViewHolder(View itemsView) {
            super(itemsView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            title = itemView.findViewById(R.id.nameTitleTextView);
            date_published = itemView.findViewById(R.id.dateTextview);
            volume = itemView.findViewById(R.id.volumeTextView);
            year = itemView.findViewById(R.id.YearTextView);
            doi = itemView.findViewById(R.id.DoiTextView);
        }

        void bindData(final ElementJournalList item){
         //   iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            title.setText(item.getTitle());
            date_published.setText(item.getDate_published());
            volume.setText(item.getVolume());
            year.setText(item.getYear());
            doi.setText(item.getDoi());
            Glide.with(context).load(item.getCover()).into(iconImage);
        }
    }

}
