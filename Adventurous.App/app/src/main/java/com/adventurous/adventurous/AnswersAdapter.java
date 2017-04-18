package com.adventurous.adventurous;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AnswersAdapter extends ArrayAdapter<String> {

    Context context;
    int layoutResourceId;
    String answers[] = null;
    public boolean isSet[];

    public AnswersAdapter(Context _listener, int _layoutResourceId, String[] _answers) {

        super(_listener, _layoutResourceId, _answers);
        isSet = new boolean[_answers.length];
        layoutResourceId = _layoutResourceId;
        context = _listener;
        answers = _answers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(layoutResourceId, parent, false);

        String answer = answers[position];

        TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
        textViewItem.setText(answer);

        if(isSet[position]){
            textViewItem.setAlpha(0.2f);
        }

        return textViewItem;
    }
}
