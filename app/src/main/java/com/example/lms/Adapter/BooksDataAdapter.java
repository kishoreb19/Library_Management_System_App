package com.example.lms.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.R;
import com.example.lms.UserDatabase;

import java.util.ArrayList;

public class BooksDataAdapter extends RecyclerView.Adapter<BooksDataAdapter.ViewHolder> {
    Context context;
    ArrayList<BooksDataSet> obj;
    public BooksDataAdapter(Context context , ArrayList<BooksDataSet> obj){
        this.context = context;
        this.obj = obj;
    }
    @NonNull
    @Override
    public BooksDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booksdataitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksDataAdapter.ViewHolder holder, int position) {
        holder.bId.setText(Integer.toString(obj.get(position).b_id));
        holder.bName.setText(obj.get(position).b_name);
        holder.bAuthor.setText(obj.get(position).b_author);
        holder.bSubject.setText(obj.get(position).b_subject);
        holder.del_img_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDatabase db = new UserDatabase(context);
                long x = db.delBooksRecord(holder.bId.getText().toString(),holder.bName.getText().toString());
                if(x != -1){
                    Toast.makeText(context, "Deleted Successfully !", Toast.LENGTH_SHORT).show();
                    obj.remove(position);
                    notifyItemRemoved(position);
                }
                else{
                    Toast.makeText(context, "Failed !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.bId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog d = new Dialog(context);
                d.setContentView(R.layout.updatebooklayout);
                d.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return obj.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bId,bName,bAuthor,bSubject;
        ImageView del_img_books;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bId = (TextView)itemView.findViewById(R.id.bId);
            bName = (TextView)itemView.findViewById(R.id.bName);
            bAuthor = (TextView)itemView.findViewById(R.id.bAuthor);
            bSubject = (TextView)itemView.findViewById(R.id.bSubj);
            del_img_books = (ImageView) itemView.findViewById(R.id.del_img_books);
        }
    }
}
