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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {


    private ArrayList<ItemModel> listItem;
    private Context context;


    public AnnouncementAdapter(ArrayList<ItemModel> items, Context ctx) {

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
        final String date  =  listItem.get(position).getTime();

        holder.itemTitle.setText(title);
        holder.itemWho.setText(who);
        holder.itemWhat.setText(what);
        holder.itemWhen.setText(when);
        holder.itemWhere.setText(where);
        holder.date.setText(date);


        holder.ltListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(DetailActivityUser.getActIntent((Activity) context).putExtra("data", listItem.get(position)));

            }
        });

        holder.cvListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(DetailActivityUser.getActIntent((Activity) context).putExtra("data", listItem.get(position)));

            }
        });

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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvListing   = (CardView) itemView.findViewById(R.id.cv_listing);
            ltListing   = (LinearLayout) itemView.findViewById(R.id.layoutListing);
            itemTitle    = (TextView) itemView.findViewById(R.id.item_title);
            itemWho  = (TextView) itemView.findViewById(R.id.item_who);
            itemWhat  = (TextView) itemView.findViewById(R.id.item_what);
            itemWhen  = (TextView) itemView.findViewById(R.id.item_when);
            itemWhere  = (TextView) itemView.findViewById(R.id.item_where);

            date  = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
