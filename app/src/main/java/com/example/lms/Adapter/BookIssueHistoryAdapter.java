package com.example.lms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.R;
import com.example.lms.UserDatabase;

import java.util.ArrayList;

public class BookIssueHistoryAdapter extends RecyclerView.Adapter<BookIssueHistoryAdapter.ViewHolder>{
    ArrayList<BookIssueHistoryDataSet> obj;
    Context context;
    public BookIssueHistoryAdapter(Context context,ArrayList<BookIssueHistoryDataSet>obj){
        this.context = context;
        this.obj = obj;
    }
    @NonNull
    @Override
    public BookIssueHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_issue_history_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.history_user_emaill.setText(obj.get(position).user_email);
        holder.history_user_bookk.setText(obj.get(position).book_name);
        holder.history_user_date.setText(obj.get(position).issue_date);
        holder.returned_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDatabase db = new UserDatabase(context);
                db.returnedBook(obj.get(position).user_email);
                obj.remove(position);
                notifyItemChanged(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return obj.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView history_user_emaill,history_user_bookk,history_user_date;
        Button returned_btn;
        public ViewHolder(View itemView){
            super(itemView);
            history_user_emaill = (TextView)itemView.findViewById(R.id.history_user_emaill);
            history_user_bookk = (TextView)itemView.findViewById(R.id.history_user_bookk);
            history_user_date = (TextView)itemView.findViewById(R.id.history_user_date);
            returned_btn = (Button) itemView.findViewById(R.id.returned_btn);
        }
    }
}
