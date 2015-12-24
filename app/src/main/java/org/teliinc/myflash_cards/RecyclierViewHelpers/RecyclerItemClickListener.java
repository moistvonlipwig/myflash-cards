package org.teliinc.myflash_cards.RecyclierViewHelpers;

/**
 * Created by cteli on 12/12/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.teliinc.myflash_cards.Activties.DisplayCardActivity;
import org.teliinc.myflash_cards.Model.FlashCard;
import org.teliinc.myflash_cards.Model.FlashCardTag;

import java.util.ArrayList;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;
    private Context context;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        this.context = context;
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        int position = view.getChildAdapterPosition(childView);
        if (childView != null) {
            // Check Preferences
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(view.getContext());
            int browseType = Integer.parseInt(sharedPref.getString("pref_browseType", "0"));

            Intent intent = new Intent(view.getContext(), DisplayCardActivity.class);
            Bundle extras = ((Activity)context).getIntent().getExtras();
            ArrayList<String> tagsList = new ArrayList<>();
            if(browseType==0) {
                intent.putExtra("QUESTION", FlashCard.RetreivedFlashCards.get(position).getQuestion());
                intent.putExtra("ANSWER", FlashCard.RetreivedFlashCards.get(position).getAnswer());
                tagsList.addAll(FlashCard.RetreivedFlashCards.get(position).getTags());
            } else {
                String tag = extras.getString("TAG");
                intent.putExtra("QUESTION", FlashCardTag.tagQuestions.get(tag).RetreivedQuestions.get(position).getQuestion());
                intent.putExtra("ANSWER", FlashCardTag.tagQuestions.get(tag).RetreivedQuestions.get(position).getAnswer());
                tagsList.addAll(FlashCardTag.tagQuestions.get(tag).RetreivedQuestions.get(position).getTags());

            }
            intent.putStringArrayListExtra("TAGS", tagsList);
            view.getContext().startActivity(intent);
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}