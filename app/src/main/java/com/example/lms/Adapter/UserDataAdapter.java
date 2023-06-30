package com.example.lms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.R;
import com.example.lms.UserDatabase;

import java.util.ArrayList;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder> {
    Context context;
    ArrayList<UserDataSet>obj;
    public UserDataAdapter(Context context, ArrayList<UserDataSet> obj){
        this.context = context;
        this.obj = obj;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userdataitem,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.u_type.setText(obj.get(position).u_type);
        holder.u_name.setText(obj.get(position).u_name);
        holder.u_email.setText(obj.get(position).u_email);
        holder.del_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDatabase db = new UserDatabase(context.getApplicationContext());
                long x = db.delRecord(obj.get(position).u_email,obj.get(position).u_type);
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
    }

    @Override
    public int getItemCount() {
        return obj.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView u_type,u_name,u_email;
        ImageView del_img;
        public ViewHolder(View itemView){
            super(itemView);
            u_type = (TextView) itemView.findViewById(R.id.uType);
            u_name = (TextView) itemView.findViewById(R.id.uName);
            u_email = (TextView) itemView.findViewById(R.id.uEmail);
            del_img = (ImageView) itemView.findViewById(R.id.delimg);
        }
    }
}
