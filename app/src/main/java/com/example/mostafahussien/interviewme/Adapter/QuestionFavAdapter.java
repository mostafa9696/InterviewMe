package com.example.mostafahussien.interviewme.Adapter;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mostafahussien.interviewme.Model.FavoriteQuestion;
import com.example.mostafahussien.interviewme.Model.QuestionAnswer;
import com.example.mostafahussien.interviewme.R;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.like.LikeButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class QuestionFavAdapter extends RecyclerView.Adapter<QuestionFavAdapter.ViewHolder> {
    private Context context;
    ArrayList<FavoriteQuestion> favoriteQuestions;
    private SparseBooleanArray expandState;                 // map integer to boolean with more memory efficient than using a HashMap to map Integers to Booleans
    Typeface typeface;

    public QuestionFavAdapter(Context context, ArrayList<FavoriteQuestion> favoriteQuestions) {
        this.context = context;
        this.favoriteQuestions = favoriteQuestions;
        expandState=new SparseBooleanArray();
        typeface=Typeface.createFromAsset(context.getAssets(),"HAMMOCK-Black.otf");
        for(int i=0 ; i<favoriteQuestions.size() ; i++){
            expandState.append(i,false);
        }

    }

    @Override
    public QuestionFavAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuestionFavAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final QuestionFavAdapter.ViewHolder holder, final int position) {
        FavoriteQuestion favoriteQuestion=favoriteQuestions.get(position);
        holder.setIsRecyclable(false);

        holder.question.setText(favoriteQuestion.getQuestion());
        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.likeButton.setVisibility(View.GONE);
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                changeRotate(holder.expand_arrow,180f,0f).start();
                expandState.put(position,true);
            }
            @Override
            public void onPreClose() {
                changeRotate(holder.expand_arrow,0f,180f).start();
                expandState.put(position,false);
            }
        });
        holder.expand_arrow.setRotation(expandState.get(position)?0f:180f);
        holder.expand_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Expandable child item
                holder.expandableLayout.toggle();
            }
        });
        holder.answer.setText(favoriteQuestion.getAnswer());

        if(position>0&&favoriteQuestion.getTopic_name().equals(favoriteQuestions.get(position-1).getTopic_name())){
            holder.topic_header.setVisibility(View.GONE);
        } else {
            holder.topic_header.setVisibility(View.VISIBLE);
            holder.topic_header.setText(favoriteQuestion.getTopic_name());
            holder.topic_header.setTypeface(typeface);
        }
    }
    private ObjectAnimator changeRotate(ImageView linearLayout , float from, float to){
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(linearLayout,"rotation",from,to);
        objectAnimator.setDuration(350);
        objectAnimator.setInterpolator(com.github.aakira.expandablelayout.Utils.createInterpolator(com.github.aakira.expandablelayout.Utils.LINEAR_INTERPOLATOR));
        return objectAnimator;
    }
    @Override
    public int getItemCount() {
        return favoriteQuestions.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView question,answer,topic_header;
        ExpandableLinearLayout expandableLayout;
        ImageView expand_arrow;
        LikeButton likeButton;
        public ViewHolder(View itemView) {
            super(itemView);
            question=(TextView)itemView.findViewById(R.id.question_id);
            answer=(TextView)itemView.findViewById(R.id.answer_id);
            expand_arrow=(ImageView) itemView.findViewById(R.id.expand_arrow);
            expandableLayout=(ExpandableLinearLayout)itemView.findViewById(R.id.expandable_list);
            topic_header=(TextView)itemView.findViewById(R.id.header_separator);
            likeButton=(LikeButton)itemView.findViewById(R.id.fav_button);
        }
    }
}
