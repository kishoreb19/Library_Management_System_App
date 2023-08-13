package com.example.lms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.BooksAvailable;
import com.example.lms.R;

import java.util.ArrayList;

public class BooksAvailableAdapter extends RecyclerView.Adapter<BooksAvailableAdapter.ViewHolder> {

    Context context;
    ArrayList<BooksDataSet> obj;
    public BooksAvailableAdapter(Context context , ArrayList<BooksDataSet> obj){
        this.context = context;
        this.obj = obj;
    }

    @NonNull
    @Override
    public BooksAvailableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.issue_book_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAvailableAdapter.ViewHolder holder, int position) {
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left));
        holder.B_idd.setText(Integer.toString(obj.get(position).b_id));
        holder.B_namee.setText(obj.get(position).b_name);
        holder.B_Authorr.setText(obj.get(position).b_author);
        holder.B_subjectt.setText(obj.get(position).b_subject);
        holder.issue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Login to issue book !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return obj.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView B_idd,B_namee,B_Authorr,B_subjectt;
        Button issue_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            B_idd = (TextView)itemView.findViewById(R.id.bIdd);
            B_namee = (TextView)itemView.findViewById(R.id.bNamee);
            B_Authorr = (TextView)itemView.findViewById(R.id.bAuthorr);
            B_subjectt = (TextView)itemView.findViewById(R.id.bSubjj);
            issue_btn = (Button) itemView.findViewById(R.id.issue_btn);
        }
    }
}
