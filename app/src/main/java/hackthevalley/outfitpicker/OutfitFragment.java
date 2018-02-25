package hackthevalley.outfitpicker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
    ArrayList<String> names;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outfit, container, false);
        ButterKnife.bind(this, view);

        mContext = getContext();
        urls = getArguments().getStringArrayList("url");
        names = getArguments().getStringArrayList("name");
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));

        //generate al the outfits here!!
        //for now, hardcoded data:
        List<ArrayList<String>> deck = generateDeck();
        for (ArrayList<String> images : deck) {
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

    public List<ArrayList<String>> generateDeck() {
        List<ArrayList<String>> deck = new ArrayList<>();
        List<String> shirts = new ArrayList<>();
        List<String> trousers = new ArrayList<>();
        List<String> shoes = new ArrayList<>();
        List<String> coats = new ArrayList<>();
        List<String> accessories = new ArrayList<>();
        for (int i = 0; i < urls.size(); ++i) {
            switch (names.get(i)) {
                case "shirt":
                    shirts.add(urls.get(i));
                    break;
                case "trouser":
                    trousers.add(urls.get(i));
                    break;
                case "shoe":
                    shoes.add(urls.get(i));
                    break;
                case "coat":
                    coats.add(urls.get(i));
                    break;
                case "accessory":
                    accessories.add(urls.get(i));
                    break;
            }
        }
        for (String top : shirts) {
            for (String bottom : trousers) {
                ArrayList<String> topAndBottom = new ArrayList<>();
                topAndBottom.add(top);
                topAndBottom.add(bottom);
                deck.add(topAndBottom);
            }
        }

        if (accessories.size() > 0) {
            for (int j = 0; j < deck.size(); j++) {
                if (j % 2 == 0) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, accessories.size());
                    deck.get(j).add(accessories.get(randomNum));
                }
            }
        }

        if (coats.size() > 0) {
            for (int j = 0; j < deck.size(); j++) {
                if (j != 0 && j % 3 == 0) {
                    continue;
                } else {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, coats.size());
                    deck.get(j).add(coats.get(randomNum));
                }
            }
        }

        if (shoes.size() > 0) {
            for (int j = 0; j < deck.size(); j++) {
                if (j != 0 && j % 5 == 0) {
                    continue;
                } else {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, shoes.size());
                    deck.get(j).add(shoes.get(randomNum));
                }
            }
        }

        Collections.shuffle(deck);
        return deck;
    }
}
