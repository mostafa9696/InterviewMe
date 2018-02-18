package com.example.mostafahussien.interviewme.Adapter;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mostafahussien.interviewme.Listener.OnTopicPressListener;
import com.example.mostafahussien.interviewme.Model.Topic;
import com.example.mostafahussien.interviewme.R;
import com.example.mostafahussien.interviewme.utils.ImageUtil;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Topic> topics;
    private OnTopicPressListener topicPressListener;
    private TypedArray images;
    public TopicAdapter(Context context, ArrayList<Topic> topics, OnTopicPressListener topicPressListener) {
        this.context = context;
        this.topics = topics;
        this.topicPressListener = topicPressListener;
        images=context.getResources().obtainTypedArray(R.array.android_drawable);

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String topicName=topics.get(position).getName();
        Picasso.with(context).load(images.getResourceId(position,R.drawable.noimage)).into(holder.imageView);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"Kurale-Regular.ttf");
        holder.textView.setText(topicName);
        holder.textView.setTypeface(typeface);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topicPressListener.onSelect(position,holder.imageView);
            }
        });
    }
    @Override
    public int getItemCount() {
        return topics.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.topic_img);
            textView=(TextView)itemView.findViewById(R.id.topic_name);
            view=itemView;
        }
    }

}
