package com.example.lms.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left));
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
        //Updating Books
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            TextView ub_name,ub_author,ub_subject;
            Button u_btn;
            @Override
            public void onClick(View view) {
                Dialog d = new Dialog(context);
                d.setContentView(R.layout.updatebooklayout);


                ub_name = (TextView) d.findViewById(R.id.ub_name);
                ub_author = (TextView) d.findViewById(R.id.ub_author);
                ub_subject = (TextView) d.findViewById(R.id.ub_subject);
                u_btn = (Button)d.findViewById(R.id.ub_btn);
                ub_name.setText(obj.get(position).b_name);
                ub_author.setText(obj.get(position).b_author);
                ub_subject.setText(obj.get(position).b_subject);
                u_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UserDatabase db = new UserDatabase(context);
                        if(ub_name.getText().toString().length()==0 || ub_author.getText().toString().length()==0 || ub_subject.getText().toString().length()==0){
                            Toast.makeText(context, "Please fill all the details !", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            long x =db.updateBooks(obj.get(position).b_id,ub_name.getText().toString(),ub_author.getText().toString(),ub_subject.getText().toString());
                            if(x != -1){
                                Toast.makeText(context, "Updated Successfully !", Toast.LENGTH_SHORT).show();
                                d.dismiss();

                                obj.get(position).b_name = ub_name.getText().toString();
                                obj.get(position).b_author = ub_author.getText().toString();
                                obj.get(position).b_subject = ub_subject.getText().toString();
                                notifyItemChanged(position);
                            }
                            else{
                                Toast.makeText(context, "Update Failed !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
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
