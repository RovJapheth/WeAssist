package com.ambongan.weassist;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder>{

    private ArrayList<StudentListModel> listItem;
    private Context context;
    OnDeletClickListener deletclick;

    public StudentListAdapter(ArrayList<StudentListModel> items, Context ctx, OnDeletClickListener deletclick) {
        listItem = items;
        context = ctx;
        this.deletclick = deletclick;
    }


    @NonNull
    @Override
    public StudentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlist_item, parent, false);

        StudentListAdapter.ViewHolder vh = new StudentListAdapter.ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull StudentListAdapter.ViewHolder holder, final int position) {


        final String username   = listItem.get(position).getUsername();
        final String emailid  = listItem.get(position).getEmailid();
        final String firstname   = listItem.get(position).getFirstname();
        final String lastname   = listItem.get(position).getLastname();
        final String gender   = listItem.get(position).getGender();
        final String grade   = listItem.get(position).getGrade();
        final String password   = listItem.get(position).getPassword();
        final String as   = listItem.get(position).getAs();
        final String key  = listItem.get(position).getKey();


        holder.item_username.setText(username);
        holder.item_email.setText(emailid);
        holder.item_firstname.setText(firstname);
        holder.item_lastname.setText(lastname);
        holder.item_gender.setText(gender);
        holder.item_grade.setText(grade);
        holder.item_password.setText(password);
        holder.item_asa.setText(as);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                StudentListActivity.deleteBox();
//                context.startActivity();
                deletclick.onDeletItemClick(key);
            }
        });

//        holder.cvListing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(DetailActivity.getActIntent((Activity) context).putExtra("data", listItem.get(position)));
//            }
//        });

    }


    @Override
    public int getItemCount() {

        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cvListing;
        LinearLayout ltListing;
        TextView item_username;
        TextView item_email;
        TextView item_firstname;
        TextView item_lastname;
        TextView item_gender;
        TextView item_grade;
        TextView item_password;
        TextView item_asa;
        ImageView delete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvListing   = (CardView) itemView.findViewById(R.id.cv_listing);
            ltListing   = (LinearLayout) itemView.findViewById(R.id.layoutListing);
            item_username    = (TextView) itemView.findViewById(R.id.item_username);
            item_email  = (TextView) itemView.findViewById(R.id.item_email);
            item_firstname  = (TextView) itemView.findViewById(R.id.item_firstname);
            item_lastname  = (TextView) itemView.findViewById(R.id.item_lastname);
            item_gender  = (TextView) itemView.findViewById(R.id.item_gender);
            item_grade  = (TextView) itemView.findViewById(R.id.item_grade);
            item_password  = (TextView) itemView.findViewById(R.id.item_password);
            item_asa  = (TextView) itemView.findViewById(R.id.item_asa);
            delete  = (ImageView) itemView.findViewById(R.id.delete);

        }
    }

}
