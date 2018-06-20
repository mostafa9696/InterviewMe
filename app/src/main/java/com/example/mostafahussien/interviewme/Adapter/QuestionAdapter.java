package com.example.mostafahussien.interviewme.Adapter;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mostafahussien.interviewme.Interface.OnSelectFavorite;
import com.example.mostafahussien.interviewme.Model.QuestionAnswer;
import com.example.mostafahussien.interviewme.Model.Topic;
import com.example.mostafahussien.interviewme.R;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.like.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<QuestionAnswer> questionAnswers;
    private SparseBooleanArray expandState;                 // map integer to boolean with more memory efficient than using a HashMap to map Integers to Booleans
    private OnSelectFavorite onSelectFavorite;
    private int textSize,questionColor,answerColor;
    SharedPreferences prefs;
    public QuestionAdapter(Context context, ArrayList<QuestionAnswer> questionAnswers,OnSelectFavorite onSelectFavorite) {
        this.onSelectFavorite=onSelectFavorite;
        this.context = context;
        this.questionAnswers = questionAnswers;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        questionColor=prefs.getInt("question_color",R.color.questionText);
        answerColor=prefs.getInt("answer_color",R.color.answerText);
        textSize= prefs.getInt("text_size",20);
        expandState=new SparseBooleanArray();
        for(int i=0 ; i<questionAnswers.size() ; i++){
            expandState.append(i,false);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if(questionAnswers.get(position).isExpandable()) {       // with child case it is always use in this case
            return 1;
        } else {            // not use parent only case it is only for knowing
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Toast.makeText(context,"onCreateViewHolder "+viewType,Toast.LENGTH_SHORT).show();
        if(viewType==0) {
            LayoutInflater inflater=(LayoutInflater.from(parent.getContext()));
            View view=inflater.inflate(R.layout.question_item, parent, false);
            return new ParentViewHolder(view);
        } else {
            LayoutInflater inflater=(LayoutInflater.from(parent.getContext()));
            View view=inflater.inflate(R.layout.question_with_child, parent, false);
            return new ParentChildViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType())
        {
            case 0: {                                                                   // not used just for know
                ParentViewHolder parentViewHolder = (ParentViewHolder) holder;
                QuestionAnswer questionAnswer=questionAnswers.get(position);
                parentViewHolder.setIsRecyclable(false);
                parentViewHolder.question.setText(questionAnswer.getQuestion());
            }
            break;
            case 1:{
                final ParentChildViewHolder childViewHolder = (ParentChildViewHolder) holder;
                QuestionAnswer questionAnswer=questionAnswers.get(position);
                childViewHolder.setIsRecyclable(false);
                childViewHolder.question.setText(questionAnswer.getQuestion());
                childViewHolder.question.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
                childViewHolder.question.setTextColor(questionColor);
                childViewHolder.expandableLayout.setInRecyclerView(true);
                childViewHolder.expandableLayout.setExpanded(expandState.get(position));
                childViewHolder.expand_arrow.setClickable(true);
                childViewHolder.likeButton.setClickable(true);
                childViewHolder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
                    @Override
                    public void onPreOpen() {
                        changeRotate(childViewHolder.expand_arrow,180f,0f).start();
                        expandState.put(position,true);
                    }
                    @Override
                    public void onPreClose() {
                        changeRotate(childViewHolder.expand_arrow,0f,180f).start();
                        expandState.put(position,false);
                    }
                });
                childViewHolder.expand_arrow.setRotation(expandState.get(position)?0f:180f);
                childViewHolder.expand_arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Expandable child item
                        childViewHolder.expandableLayout.toggle();
                    }
                });
                childViewHolder.answer.setText(questionAnswer.getAnswer());
                childViewHolder.answer.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
                childViewHolder.answer.setTextColor(answerColor);
                if(questionAnswers.get(position).isFav()){
                    childViewHolder.likeButton.setLiked(true);
                }
            }
            break;
            default:
                break;
        }
    }
    private ObjectAnimator changeRotate(ImageView linearLayout , float from,float to){
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(linearLayout,"rotation",from,to);
        objectAnimator.setDuration(350);
        objectAnimator.setInterpolator(com.github.aakira.expandablelayout.Utils.createInterpolator(com.github.aakira.expandablelayout.Utils.LINEAR_INTERPOLATOR));
        return objectAnimator;
    }

    /*@Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.question.setText(questionAnswers.get(position).getQuestion());
    }*/

    @Override
    public int getItemCount() {
        return questionAnswers.size();
    }

    public class ParentViewHolder extends RecyclerView.ViewHolder{            // holder without child expand (not used)
        TextView question;
        LikeButton likeButton;
        public ParentViewHolder(View itemView) {
            super(itemView);
            question=(TextView)itemView.findViewById(R.id.question_id);
            likeButton=(LikeButton)itemView.findViewById(R.id.fav_button);
        }
    }
    public class ParentChildViewHolder extends RecyclerView.ViewHolder implements OnLikeListener{            // holder with child expand
        TextView question,answer;
        LikeButton likeButton;
        ExpandableLinearLayout expandableLayout;
        ImageView expand_arrow;
        public ParentChildViewHolder(View itemView) {
            super(itemView);
            question=(TextView)itemView.findViewById(R.id.question_id);
            likeButton=(LikeButton)itemView.findViewById(R.id.fav_button);
            answer=(TextView)itemView.findViewById(R.id.answer_id);
            expand_arrow=(ImageView) itemView.findViewById(R.id.expand_arrow);
            expandableLayout=(ExpandableLinearLayout)itemView.findViewById(R.id.expandable_list);
            likeButton.setOnLikeListener(this);
        }
        @Override
        public void liked(LikeButton likeButton) {
            onSelectFavorite.onSelect(getAdapterPosition(),"like");
            questionAnswers.get(getAdapterPosition()).setFav(true);
        }
        @Override
        public void unLiked(LikeButton likeButton) {
            onSelectFavorite.onSelect(getAdapterPosition(),"unLike");
            questionAnswers.get(getAdapterPosition()).setFav(false);
        }
    }
    public void updateViews(int textSize,int selectedAnswerColor,int selectedQuestionColor){
        this.textSize=textSize;
        answerColor=selectedAnswerColor;
        questionColor=selectedQuestionColor;
        notifyDataSetChanged();
    }
}
