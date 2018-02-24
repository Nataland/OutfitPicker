package hackthevalley.outfitpicker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by natalie on 2018-02-24.
 */

public class ClosetFragment extends Fragment {
    @BindView(R.id.add_more_items_floating_button)
    FloatingActionButton addMoreButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet, container, false);
        ButterKnife.bind(this, view);

        addMoreButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_a_photo_white_24dp));

        return view;
    }
}
