package hackthevalley.outfitpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.Matrix;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by natalie on 2018-02-24.
 */

public class SingleImageView extends LinearLayout {
    @BindView(R.id.clothing_item_image)
    ImageView clothingView;

    public SingleImageView(Context context) {
        super(context);
        init();
    }

    public SingleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SingleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.single_clothing_item_fragment, this);
        ButterKnife.bind(this, view);
    }
}
