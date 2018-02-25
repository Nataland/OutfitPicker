package hackthevalley.outfitpicker;

import android.content.Context;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalie on 2018-02-24.
 */

@Layout(R.layout.tinder_card_view)
public class TinderCard {

    @View(R.id.outfit_items_container)
    private GridView outfitContainer;

    private Context mContext;
    private ArrayList<String> outfitImages;
    private SwipePlaceHolderView mSwipeView;


    public TinderCard(Context context, ArrayList<String> outfit, SwipePlaceHolderView swipeView) {
        mContext = context;
        outfitImages = outfit;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved() {
        outfitContainer.setAdapter(new ImageOutfitMatcherAdapter(mContext, outfitImages));
    }

    @SwipeOut
    private void onSwipedOut() {
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState() {
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState");
    }

}
