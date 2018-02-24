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

        ImageGalleryAdapter adapter = new ImageGalleryAdapter(getActivity(), ClothePhoto.getSpacePhotos());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {

            @Override
            public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                Context context = parent.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);

                // Inflate the layout
                View photoView = inflater.inflate(R.layout.item_photo, parent, false);

                ImageGalleryAdapter.MyViewHolder viewHolder = new ImageGalleryAdapter.MyViewHolder(photoView);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position) {

                ClothePhoto clothePhoto = mClothePhotos[position];
                ImageView imageView = holder.mPhotoImageView;

                Glide.with(mContext)
                        .load(clothePhoto.getUrl())
                        .placeholder(R.drawable.ic_cloud_off_red)
                        .into(imageView);
            }

            @Override
            public int getItemCount() {
                return (mClothePhotos.length);
            }

            public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

                public ImageView mPhotoImageView;

                public MyViewHolder(View itemView) {

                    super(itemView);
                    mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
                    itemView.setOnClickListener(this);
                }

                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        ClothePhoto clothePhoto = mClothePhotos[position];

                        Intent intent = new Intent(mContext, ClothePhotoActivity.class);
                        intent.putExtra(ClothePhotoActivity.EXTRA_SPACE_PHOTO, clothePhoto);
                        startActivity(intent);
                    }
                }
            }

            private ClothePhoto[] mClothePhotos;
            private Context mContext;

            public ImageGalleryAdapter(Context context, ClothePhoto[] clothePhotos) {
                mContext = context;
                mClothePhotos = clothePhotos;
            }
}
}
