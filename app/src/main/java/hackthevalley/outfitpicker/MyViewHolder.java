package hackthevalley.outfitpicker;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by natalie on 2018-02-24.
 */

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView mPhotoImageView;

    MyViewHolder(View itemView) {
        super(itemView);
        mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            ClothePhoto clothePhoto = ClothePhoto.getSpacePhotos()[position];

            Intent intent = new Intent(view.getContext(), ClothePhotoActivity.class);
            intent.putExtra(ClothePhotoActivity.EXTRA_SPACE_PHOTO, clothePhoto);
            view.getContext().startActivity(intent);
        }
    }
}
