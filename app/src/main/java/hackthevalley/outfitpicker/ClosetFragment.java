package hackthevalley.outfitpicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by natalie on 2018-02-24.
 */

public class ClosetFragment extends Fragment {
    @BindView(R.id.add_more_items_floating_button)
    FloatingActionButton addMoreButton;

    @BindView(R.id.rv_images)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet, container, false);
        ButterKnife.bind(this, view);

        addMoreButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_a_photo_white_24dp));

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        Log.d("", getActivity().toString());
        Log.d("", "is it null?");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ImageGalleryAdapter adapter = new ImageGalleryAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
