package hackthevalley.outfitpicker;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by natalie on 2018-02-24.
 */

public class ClosetFragment extends Fragment {
    @BindView(R.id.add_more_items_floating_button)
    FloatingActionButton addMoreButton;

    @BindView(R.id.rv_images)
    RecyclerView recyclerView;

    @BindView(R.id.imageCamera)
    ImageView imageCamera;

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

    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;

    @OnClick(R.id.add_more_items_floating_button)
    void onShowClick(){
        takePhoto();
    }

    static final int REQUEST_IMAGE_CAPTURE = 100;

    public void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
//        imageUri = Uri.fromFile(photo);
//        ClosetFragment.this.startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageCamera.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }

//        if (requestCode == REQUEST_IMAGE_CAPTURE) {
//            if (resultCode == Activity.RESULT_OK) {
//                Uri selectedImage = imageUri;
//                Log.d("", data.toString());
//                Log.d("", "yoyoyo");
//                getActivity().getContentResolver().notifyChange(selectedImage, null);
//                ContentResolver cr = getActivity().getContentResolver();
//                Bitmap bitmap;
//
//                try {
//                    bitmap = android.provider.MediaStore.Images.Media
//                            .getBitmap(cr, selectedImage);
//
//                    imageCamera.setImageBitmap(bitmap);
//                    Toast.makeText(getActivity(), selectedImage.toString(),
//                            Toast.LENGTH_LONG).show();
//                } catch (Exception e) {
//                    Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT)
//                            .show();
//                    Log.e("Camera", e.toString());
//                }
//            }
//        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mRestaurant.getPushId())
                .child("imageUrl");
        ref.setValue(imageEncoded);
    }

}
