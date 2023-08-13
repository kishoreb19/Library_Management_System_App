package com.example.lms.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lms.R;
import com.example.lms.UserDatabase;

import java.time.LocalDate;
import java.util.ArrayList;

public class BookIssueAdapter extends RecyclerView.Adapter<BookIssueAdapter.ViewHolder> {
    ArrayList<BooksDataSet>obj;
    Context context;
    public BookIssueAdapter(Context context,ArrayList<BooksDataSet>obj){
        this.context = context;
        this.obj = obj;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.issue_book_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left));
        holder.B_idd.setText(Integer.toString(obj.get(position).b_id));
        holder.B_namee.setText(obj.get(position).b_name);
        holder.B_Authorr.setText(obj.get(position).b_author);
        holder.B_subjectt.setText(obj.get(position).b_subject);
        holder.issue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sending E-mail
                //String[] recipientList = new UserDatabase(context).getLibrarians();
                ArrayList<String> data = new UserDatabase(context).getLibrarians();
                String Subject = "Book Issue";
                String msg = null;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    msg = "This is to request you that I would like to issue the following book:" +

                            "\nBook ID: "+holder.B_idd.getText().toString()+
                            "\nBook Name: "+holder.B_namee.getText().toString()+
                            "\nBook Author: "+holder.B_Authorr.getText().toString()+
                            "\nBook Subject: "+holder.B_subjectt.getText().toString()+
                            "\nDate of Issue: "+ LocalDate.now()+
                            "\nValidity of Issue: 30 Days"+
                            "\n\nKindly approve.";
                }

                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL,data);
                i.putExtra(Intent.EXTRA_SUBJECT,Subject);
                i.putExtra(Intent.EXTRA_TEXT,msg);
                i.setType("message/rfc822");
                context.startActivity(Intent.createChooser(i,"Choose an Email Client "));
            }
        });
    }

    @Override
    public int getItemCount() {
        return obj.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView B_idd,B_namee,B_Authorr,B_subjectt;
        Button issue_btn;
        public ViewHolder(View itemView){
            super(itemView);
            B_idd = (TextView)itemView.findViewById(R.id.bIdd);
            B_namee = (TextView)itemView.findViewById(R.id.bNamee);
            B_Authorr = (TextView)itemView.findViewById(R.id.bAuthorr);
            B_subjectt = (TextView)itemView.findViewById(R.id.bSubjj);
            issue_btn = (Button) itemView.findViewById(R.id.issue_btn);
        }
    }
}
