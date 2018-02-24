package hackthevalley.outfitpicker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by natalie on 2018-02-24.
 */

public class OutfitFragment extends Fragment {
    private Context mContext;

    @BindView(R.id.swipeView)
    SwipePlaceHolderView mSwipeView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outfit, container, false);
        ButterKnife.bind(this, view);

        mContext = getContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));


        for(Profile profile : Utils.loadProfiles(this.getContext())){
            mSwipeView.addView(new TinderCard(mContext, profile, mSwipeView));
        }

        return view;
    }

    @OnClick(R.id.rejectBtn)
    public void onRejectClick(View v) {
        mSwipeView.doSwipe(false);
    }

    @OnClick(R.id.acceptBtn)
    public void onAcceptClick(View v) {
        mSwipeView.doSwipe(true);
    }
}
