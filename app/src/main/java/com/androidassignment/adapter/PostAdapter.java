package com.androidassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.androidassignment.R;
import com.androidassignment.activity.PostActivity;
import com.androidassignment.model.PostData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyviewHolder> {

    Context context;
    ArrayList<PostData> postDataArrayList;

    retrive retriveData;

    public interface retrive {
        public void getData();

        public void getSelected(boolean b);
    }

    public PostAdapter(Context context, ArrayList<PostData> postDataArrayList) {
        this.context = context;
        this.postDataArrayList = postDataArrayList;
        retriveData = (retrive) context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyviewHolder(LayoutInflater.from(context).inflate(R.layout.post_item, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, final int position) {

        final PostData data = postDataArrayList.get(position);

        holder.title.setText(data.getTitle());
        holder.createAt.setText(data.getCreated_at());

        holder.postSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    retriveData.getSelected(true);
                    holder.mainCard.setCardBackgroundColor(context.getResources().getColor(R.color.gray));
                } else {
                    retriveData.getSelected(false);
                    holder.mainCard.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.postSwitch.isChecked()) {
                    holder.postSwitch.setChecked(false);
                } else {
                    holder.postSwitch.setChecked(true);
                }

            }
        });

        if (position == postDataArrayList.size() - 1) {
            retriveData.getData();
        }
    }

    @Override
    public int getItemCount() {
        return postDataArrayList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView title, createAt;
        SwitchCompat postSwitch;
        CardView mainCard;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTxt);
            createAt = itemView.findViewById(R.id.createatTxt);
            postSwitch = itemView.findViewById(R.id.postSwitch);
            mainCard = itemView.findViewById(R.id.itemCard);
        }
    }
}
