package org.teliinc.myflash_cards.RecyclierViewHelpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.teliinc.myflash_cards.Activties.BrowseCards;
import org.teliinc.myflash_cards.Activties.DisplayCardActivity;
import org.teliinc.myflash_cards.Model.FlashCard;
import org.teliinc.myflash_cards.Model.FlashCardTag;

import java.util.ArrayList;

/**
 * Created by cteli on 12/13/2015.
 */
public class RecyclerItemTagClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemTagClickListener(Context context, OnItemClickListener listener) {
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
            Intent intent = new Intent(view.getContext(), BrowseCards.class);
            intent.putExtra("TAG", FlashCardTag.getTags().get(position));
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