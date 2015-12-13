package org.teliinc.myflash_cards.RecyclierViewHelpers;

/**
 * Created by cteli on 12/12/2015.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.teliinc.myflash_cards.Activties.DisplayCardActivity;
import org.teliinc.myflash_cards.Model.FlashCard;

import java.util.ArrayList;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
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
        if (childView != null ) {
                Intent intent = new Intent(view.getContext(), DisplayCardActivity.class);
                intent.putExtra("QUESTION", FlashCard.RetreivedFlashCards.get(position).getQuestion());
                intent.putExtra("ANSWER", FlashCard.RetreivedFlashCards.get(position).getAnswer());
                ArrayList<String> tagsList = new ArrayList<>();
                tagsList.addAll(FlashCard.RetreivedFlashCards.get(position).getTags());
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