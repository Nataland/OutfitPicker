package hackthevalley.outfitpicker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    List<String> urls;

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


        uploads = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }

                Collections.sort(uploads, new Comparator<Upload>() {
                    @Override
                    public int compare(final Upload object1, final Upload object2) {
                        return object1.getName().compareTo(object2.getName());
                    }
                });

                urls = new ArrayList<>();
                for (Upload upload : uploads) {
                    urls.add(upload.getUrl());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Todo:
            }
        });

        mSwipeView.addView(new TinderCard(mContext, urls, mSwipeView));
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
