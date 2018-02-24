package hackthevalley.outfitpicker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by natalie on 2018-02-24.
 */

class ImageGalleryAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context mContext;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View photoView = inflater.inflate(R.layout.item_photo, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ClothePhoto clothePhoto = ClothePhoto.getSpacePhotos()[position];
        ImageView imageView = holder.mPhotoImageView;
        Glide.with(mContext)
                .load(clothePhoto.getUrl())
                .placeholder(R.drawable.ic_cloud_off_red)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return (ClothePhoto.getSpacePhotos().length);
    }

    public ImageGalleryAdapter(Context context) {
        mContext = context;
    }
}