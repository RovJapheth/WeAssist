package com.ambongan.weassist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    private ArrayList<ItemModel> listItem;
    private Context context;

    public MainActivityAdapter(ArrayList<ItemModel> items, Context ctx) {

        listItem = items;
        context = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        final String title   = listItem.get(position).getTitle();
        final String who  = listItem.get(position).getWho();
        final String what  = listItem.get(position).getWhat();
        final String when  = listItem.get(position).getWhen();
        final String where  = listItem.get(position).getWhere();
        final String date  = listItem.get(position).getTime();
        final String expireTimeStr  = listItem.get(position).getExpireTime();

        holder.itemTitle.setText(title);
        holder.itemWho.setText(who);
        holder.itemWhat.setText(what);
        holder.itemWhen.setText(when);
        holder.itemWhere.setText(where);
        holder.date.setText(date);
        holder.expireTime.setText(expireTimeStr);
//        holder.ltListing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                context.startActivity(DetailActivity.getActIntent((Activity) context).putExtra("data", listItem.get(position)));
//            }
//        });

        holder.cvListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(DetailActivity.getActIntent((Activity) context).putExtra("data", listItem.get(position)));
//                ((Activity) context).finish();
            }
        });
//        listItem.get(position).getTime().compareTo(listItem.get(position).getExpireTime()) <0 ||
//        if ( listItem.get(position).getTime().compareTo(listItem.get(position).getExpireTime()) >0){
//            holder.expireTime.setText("");
//        }

    }

    @Override
    public int getItemCount() {

        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cvListing;
        LinearLayout ltListing;
        TextView itemTitle;
        TextView itemWho;
        TextView itemWhat;
        TextView itemWhen;
        TextView itemWhere;
        TextView date;
        TextView expireTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvListing   = (CardView) itemView.findViewById(R.id.cv_listing);
            ltListing   = (LinearLayout) itemView.findViewById(R.id.layoutListing);
            itemTitle    = (TextView) itemView.findViewById(R.id.item_title);
            itemWho = (TextView) itemView.findViewById(R.id.item_who);
            itemWhat = (TextView) itemView.findViewById(R.id.item_what);
            itemWhen  = (TextView) itemView.findViewById(R.id.item_when);
            itemWhere  = (TextView) itemView.findViewById(R.id.item_where);
            expireTime  = (TextView) itemView.findViewById(R.id.expireTime);
            date  = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
