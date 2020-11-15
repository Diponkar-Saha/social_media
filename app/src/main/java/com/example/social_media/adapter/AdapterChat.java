package com.example.social_media.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.social_media.R;
import com.example.social_media.model.ModelChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyHolder> {
    private static final int MSG_TYPE_LEFT=0;
    private static final int MSG_TYPE_RIGHT=1;
    Context context;
    List<ModelChat> chatList;
    String imageUrl;
    FirebaseUser fuser;

    public AdapterChat(Context context, List<ModelChat> chatList, String imageUrl) {
        this.context = context;
        this.chatList = chatList;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT){
            View view= LayoutInflater.from(context).inflate(R.layout.row_chat_right,parent,false);
            return new MyHolder(view);
        }else{
            View view= LayoutInflater.from(context).inflate(R.layout.row_chat_left,parent,false);
            return new MyHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String message=chatList.get(position).getMessage();
        String timeStamp=chatList.get(position).getTimestamp();
        //convert time

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault());
        String dateTime = simpleDateFormat.format(new Date(Long.parseLong(timeStamp)));
        holder.messageTV.setText(message);
        holder.timeTV.setText(dateTime);
        try{
            Picasso.get().load(imageUrl).into(holder.profileIV);
        }catch (Exception e){

        }
        if(position==chatList.size()-1){
            if(chatList.get(position).isSeen()) {
                holder.isSeenTV.setText("Seen");
            }else{
                holder.isSeenTV.setText("Deliverd");
            }

        }else{
            holder.isSeenTV.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if(chatList.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }


    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{
        ImageView profileIV;
        TextView messageTV,timeTV,isSeenTV;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            profileIV=itemView.findViewById(R.id.profileIv1);
            messageTV=itemView.findViewById(R.id.messageTv1);
            timeTV=itemView.findViewById(R.id.timeTV);
            isSeenTV=itemView.findViewById(R.id.seenTV);

        }
    }
}
