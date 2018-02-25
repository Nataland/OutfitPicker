package hackthevalley.outfitpicker;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class ClosetFragment extends Fragment {
    //recyclerview object
    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;

    //database reference
    private DatabaseReference mDatabase;

    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<Upload> uploads;

    final String[] results = {""};
    private StorageReference storageReference;
    String uploadId;

    @BindView(R.id.select_clothing_type_spinner)
    Spinner selectTypeSpinner;

    @BindView(R.id.take_photo_fab)
    FloatingActionButton takePhotoBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet, container, false);
        ButterKnife.bind(this, view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView = view.findViewById(R.id.images_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        configureSpinnerUI();

        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        takePhotoBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_a_photo_white_24dp));

        progressDialog = new ProgressDialog(getContext());
        uploads = new ArrayList<>();

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        final String[] filter = new String[1];
        filter[0] = "";
        selectTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                filter[0] = selectTypeSpinner.getSelectedItem().toString().toLowerCase();
                if (filter[0].equals("show all")) {
                    filter[0] = "";
                }
                fetchValues(filter[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fetchValues(filter[0]);
        return view;
    }

    public void fetchValues(final String filter) {
        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                uploads.clear();
                //dismissing the progress dialog
                progressDialog.dismiss();

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Upload upload = postSnapshot.getValue(Upload.class);
                    if(upload.getTags().contains(filter)) {
                        uploads.add(upload);
                    }
                }
                /*
                Collections.sort(uploads, new Comparator<Upload>() {
                    @Override
                    public int compare(final Upload object1, final Upload object2) {
                        return object1.getName().compareTo(object2.getName());
                    }
                });*/

                //creating adapter
                adapter = new MyAdapter(getContext(), uploads);

                //adding adapter to recyclerview
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }

    @OnClick(R.id.take_photo_fab)
    void onAddImageClick() {
        PickImageDialog.build(new PickSetup())
                .setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {
                        uploadFile(r.getUri());
                    }
                }).show(this.getActivity());
    }

    private void configureSpinnerUI() {
        String[] selections = {"Show All", "Shirt", "Dress", "Trouser", "Coat", "Footwear", "Accessories"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_dropdown_menu, selections) {

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setGravity(Gravity.RIGHT);
                return v;
            }

        };
        selectTypeSpinner.setAdapter(adapter);
    }

    private void uploadFile(Uri filePath) {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this.getContext());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getActivity().getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            String url = taskSnapshot.getDownloadUrl().toString();

                            //creating the upload object to store uploaded image details
                            Upload upload = new Upload("Clothes", url, results[0]);

                            //adding an upload to firebase database
                            uploadId = mDatabase.push().getKey();
                            mDatabase.child(uploadId).setValue(upload);

                            // add first and then update
                            SessionTask sessionTask = new SessionTask();
                            sessionTask.execute(url);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            //display an error if no file is selected
        }
    }

    public class SessionTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String res = makeHttpsRequest(params[0]);
            return res;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
        }
    }

    public String makeHttpsRequest(String url) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"url\":\"" + url + "\"}");
        Request request = new Request.Builder()
                .url("https://eastus.api.cognitive.microsoft.com/vision/v1.0/analyze?visualFeatures=Tags&language=en")
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", "4b9e55ceddcb464dbdca03f156d126ff")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "8adc40e0-3c73-4b91-a203-b0a1947b04c2")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();

                    results[0] = responseBody.string();
                    Log.d("results[0]", results[0]);
                    mDatabase.child(uploadId).child("tags").setValue(extractTags(results[0]));
                }
            }
        });
        return results[0];
    }

    public String extractTags(String infosJSON) {

        // Create an empty ArrayList that we can start adding earthquakes to
        String tags = "";

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        Log.d("extractTags ", infosJSON);
        try {
            JSONObject jsonObject = new JSONObject(infosJSON);
            JSONArray arr = jsonObject.getJSONArray("tags");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject object = arr.getJSONObject(i);
                String feature = object.getString("name");
                if (i > 0) {
                    tags = tags + "," + feature;
                } else {
                    tags = feature;
                }
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }
        Log.d("tags ", tags);
        return tags;
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}