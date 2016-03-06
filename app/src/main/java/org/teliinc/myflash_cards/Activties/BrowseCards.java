package org.teliinc.myflash_cards.Activties;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.FirebaseRecyclerAdapter;
import com.github.magiepooh.recycleritemdecoration.ItemDecorations;

import jp.satorufujiwara.binder.Section;
import jp.satorufujiwara.binder.ViewType;
import jp.satorufujiwara.binder.recycler.RecyclerBinderAdapter;

import org.teliinc.myflash_cards.Model.FlashCard;
import org.teliinc.myflash_cards.R;
import org.teliinc.myflash_cards.RecyclierViewHelpers.RecyclerItemClickListener;

import java.util.HashSet;
import java.util.Set;

public class BrowseCards extends BaseMenuClass {

    public RecyclerView BrowseCardsView;
    private FirebaseRecyclerAdapter<FlashCard, FlashcardViewHolder1> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_cards);

        // Enable Action Bar
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.show();

        BrowseCardsView = (RecyclerView) findViewById(R.id.flash_Card_view);

        BrowseCardsView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        BrowseCardsView.setLayoutManager(mLayoutManager);
        BrowseCardsView.setItemAnimator(new DefaultItemAnimator());

        // Add decoration// create ItemDecoration
        RecyclerView.ItemDecoration decoration = ItemDecorations.vertical(this)
                .first(R.drawable.shape_decoration_green_h_16)
                .type(DemoViewType.LANDSCAPE_ITEM.ordinal(),
                        R.drawable.shape_decoration_gray_h_12_padding)
                .type(DemoViewType.LANDSCAPE_TILE.ordinal(),
                        R.drawable.shape_decoration_cornflower_lilac_h_8)
                .type(DemoViewType.LANDSCAPE_DESCRIPTION.ordinal(),
                        R.drawable.shape_decoration_red_h_8)
                .last(R.drawable.shape_decoration_flush_orange_h_16)
                .create();
        BrowseCardsView.addItemDecoration(decoration);

        // Add a OnItemTouchListener
        BrowseCardsView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    }
                })
        );

        // Check if intent is passed some data based on it being a tag based set
        Bundle extras = getIntent().getExtras();
        /*
        if (extras != null) {
            String tag = extras.getString("TAG");
            mAdapter = new FlashcardAdapter(FlashCardTag.getFlashCardSet(tag));
        } else {
            mAdapter = new FlashcardAdapter(FlashCard.RetreivedFlashCards);
        }*/
        mAdapter = new FirebaseRecyclerAdapter<FlashCard, FlashcardViewHolder1>(FlashCard.class, R.layout.flashcard_layout, FlashcardViewHolder1.class, LaunchScreenActivity.firebaseRef) {
            @Override
            public void populateViewHolder(FlashcardViewHolder1 flashcardView, FlashCard card, int position) {
                flashcardView.setQuestion(card.getQuestion());
            }
        };
        BrowseCardsView.setAdapter(mAdapter);
    }

    public static class FlashcardViewHolder1 extends RecyclerView.ViewHolder {
        View mView;

        public FlashcardViewHolder1(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setQuestion(String name) {
            TextView field = (TextView) mView.findViewById(R.id.textViewAnswer);
            field.setText(name);
        }
        public void setAnswer(String answer) {}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    public enum DemoViewType implements ViewType {
        TITLE,
        LANDSCAPE_ITEM,
        LANDSCAPE_TILE,
        LANDSCAPE_DESCRIPTION,

        PAGE;

        @Override
        public int viewType() {
            return ordinal();
        }
    }
    public enum DemoSectionType implements Section {
        ITEM
    }
}
