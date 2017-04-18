package com.adventurous.adventurous;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class AnswersFragment extends ListFragment {

    public String[] Answers;
    private View selectedView;
    private IOnAnswerSelectionChangeListener listener;
    private AnswersAdapter nameAdapter;
    private int lastPosition;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listener = (IOnAnswerSelectionChangeListener) getActivity();
        nameAdapter = new AnswersAdapter((Activity)listener, R.layout.text_view_item, Answers);
        setListAdapter(nameAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        if(v.isEnabled()){
            selectedView = v;
            lastPosition = position;
            v.setSelected(true);
            listener.OnAnswerSelect(position);
        }

    }

    public void disableLastAnswer(){
        selectedView.setEnabled(false);
        selectedView.setSelected(false);
        selectedView.setAlpha(0.2f);
        nameAdapter.isSet[lastPosition] = true;
    }
}
