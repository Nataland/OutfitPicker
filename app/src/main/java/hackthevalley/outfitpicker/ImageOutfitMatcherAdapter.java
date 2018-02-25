package hackthevalley.outfitpicker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by natalie on 2018-02-24.
 */

public class ImageOutfitMatcherAdapter extends BaseAdapter {
    private Context mContext;
    List<Upload> uploads;
    List<String> urls;
    private DatabaseReference mDatabase;

    public ImageOutfitMatcherAdapter(Context c, List<String> outfitImages) {
        mContext = c;
        urls = outfitImages;
    }

    public int getCount() {
        return uploads.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
//            int randomGeneratedAngle = ThreadLocalRandom.current().nextInt(-8, 8 + 1);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(mContext.getResources().getDimensionPixelSize(R.dimen.photo_size_small_width), mContext.getResources().getDimensionPixelSize(R.dimen.photo_size_small_height)));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            imageView = (ImageView) convertView;
        }

        Glide.with(mContext)
                .load(urls.get(position))
                .placeholder(R.drawable.ic_cloud_off_red)
                .into(imageView);

        return imageView;
    }
}
