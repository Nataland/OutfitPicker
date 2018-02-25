package hackthevalley.outfitpicker;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    List<Upload> uploads;
    ArrayList<String> urls;
    //recyclerview object
    //progress dialog
    private ProgressDialog progressDialog;

    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;

    //database reference
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outfit, container, false);
        ButterKnife.bind(this, view);

        mContext = getContext();
        urls = getArguments().getStringArrayList("array");
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));

        //generate al the outfits here!!
        //for now, hardcoded data:
        List<ArrayList<String>> deck =Arrays.asList(urls, urls, urls, urls, urls, urls);
        for (ArrayList<String> images: deck) {
            mSwipeView.addView(new TinderCard(mContext, images, mSwipeView));
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
