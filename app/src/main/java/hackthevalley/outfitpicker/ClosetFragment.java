package hackthevalley.outfitpicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        http();
//        takePhoto();
    }

    public void http() {
        OkHttpClient client = new OkHttpClient();
        System.out.println("Fany!!!");
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"url\":\"https://ufpro.com/media/uploads/public/product/-striker_xt_gen2_combat_pants_brown_grey_2.jpg\"}");
        Request request = new Request.Builder()
                .url("https://eastus.api.cognitive.microsoft.com/vision/v1.0/analyze?visualFeatures=Tags&details=Celebrities&language=en")
                .post(body).addHeader("Content-Type", "application/json")
                .addHeader("Ocp-Apim-Subscription-Key", "e7a2369b290c419fbc44a9c5eff066a9")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "d0ad4877-d999-4cbe-8987-c4b4b91994ca")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(response.toString());
                        Log.d("target", response.toString());
                    }
                });
            }

            @Override
            public void onFailure(final Call call, IOException e){
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // Error Handle here
                    }
                });
            }
        });
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
//            encodeBitmapAndSaveToFirebaseAndSaveToFirebase(imageBitmap);
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
        }
    }

//    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
//        DatabaseReference ref = FirebaseDatabase.getInstance()
//                .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .child(mRestaurant.getPushId())
//                .child("imageUrl");
//        ref.setValue(imageEncoded);
//    }
//
//}
