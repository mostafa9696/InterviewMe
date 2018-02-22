package com.example.mostafahussien.interviewme.Model;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.graphics.Color;
import com.afollestad.materialdialogs.color.ColorChooserDialog;

import com.example.mostafahussien.interviewme.Interface.OnDialogSaved;
import com.example.mostafahussien.interviewme.MainActivity;
import com.example.mostafahussien.interviewme.R;
import com.jaredrummler.android.colorpicker.ColorPickerView;

public class EditDialog extends DialogFragment implements ColorPickerView.OnColorChangedListener, View.OnClickListener{
    OnDialogSaved onDialogSaved;
    SeekBar seekBar;
    TextView answerTextView,questionTextView,sizeTextView;
    LinearLayout answerView,questionView;
    Context context;
    ColorPickerView colorPickerView;
    String viewType;
    int quesstion_color,answer_color, textSize;
    SharedPreferences prefs;
    Button saveBtn,cancelBtn;
    Dialog dialog;
    View dialogView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater=getActivity().getLayoutInflater();
        dialogView=inflater.inflate(R.layout.edit_dialog_layout,null);
        seekBar=(SeekBar)dialogView.findViewById(R.id.text_seekbar);
        onDialogSaved= (OnDialogSaved) context;
        answerTextView=(TextView)dialogView.findViewById(R.id.answer_text);
        questionTextView=(TextView)dialogView.findViewById(R.id.question_text);
        sizeTextView=(TextView)dialogView.findViewById(R.id.text_size);
        answerView=(LinearLayout)dialogView.findViewById(R.id.answer_view);
        questionView=(LinearLayout)dialogView.findViewById(R.id.question_view);
        colorPickerView = (ColorPickerView)dialogView.findViewById(R.id.cpv_color_picker_view);
        saveBtn=(Button)dialogView.findViewById(R.id.save_button);
        cancelBtn=(Button)dialogView.findViewById(R.id.cancel_button);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        quesstion_color=prefs.getInt("question_color",R.color.questionText);
        answer_color=prefs.getInt("answer_color",R.color.answerText);
        textSize= prefs.getInt("text_size",21);
        seekBar.setProgress(textSize-21);
        questionTextView.setTextColor(quesstion_color);
        answerTextView.setTextColor(answer_color);
        sizeTextView.setTextSize(textSize);
        colorPickerView.setOnColorChangedListener(this);
        ////
        intializeDialog();
        viewType="empty";
        questionView.setOnClickListener(this);
        answerView.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textSize=i+21;
                sizeTextView.setTextSize(textSize);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return dialog;
    }
    public void intializeDialog(){
        dialog=new Dialog(getActivity());
        dialog.setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                dialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    public void saveData(){
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putInt("question_color",quesstion_color);
        edit.putInt("answer_color",answer_color);
        edit.putInt("text_size",textSize);
        edit.apply();
        onDialogSaved.onSavedValues(textSize,answer_color,quesstion_color);
    }
    @Override
    public void onClick(View view) {
        int id=view.getId();
        Log.d("ee5", "onClick: "+id);
        if(id==R.id.question_view) {
            viewType="question";
            colorPickerView.setColor(quesstion_color, true);
        } else if(id==R.id.answer_view){
            viewType="answer";
            colorPickerView.setColor(answer_color, true);
        }
        colorPickerView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onColorChanged(int newColor) {
        if(viewType.equals("question")) {
            quesstion_color=newColor;
            questionTextView.setTextColor(quesstion_color);
        } else if(viewType.equals("answer")) {
            answer_color=newColor;
            answerTextView.setTextColor(answer_color);
        }
    }

}
