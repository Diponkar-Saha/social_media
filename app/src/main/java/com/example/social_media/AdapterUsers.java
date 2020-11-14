package com.example.social_media;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder> {
    Context context;
    List<ModelUser> userList;

    public AdapterUsers(Context context, List<ModelUser> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.row_users,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String userImage=userList.get(position).getImage();
        String userName=userList.get(position).getName();
        final String userEmail=userList.get(position).getEmail();

        holder.mName.setText(userName);
        holder.mEmail.setText(userEmail);
        try{
            Picasso.get().load(userImage)
                    .placeholder(R.drawable.ic_baseline_face_24)
                    .into(holder.mAvaterImage);
        }catch (Exception e){

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+userEmail,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
       if (userList==null || userList.size()==0){
           return 0;
       }else{
           return userList.size();
       }
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView mAvaterImage;
        TextView mName,mEmail;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mAvaterImage=itemView.findViewById(R.id.avaterimage);
            mName=itemView.findViewById(R.id.mnameTv);
            mEmail=itemView.findViewById(R.id.memailTv);
        }
    }
}
