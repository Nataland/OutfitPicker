package hackthevalley.outfitpicker;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    //database reference
    private DatabaseReference mDatabase;

    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<Upload> uploads;

    //list to hold all names
    private ArrayList<String> names;

    //list to hold all urls
    private ArrayList<String> urls;

    private TextView mTextMessage;
    ClosetFragment closetFragment;
    OutfitFragment outfitFragment;
    SettingsFragment settingsFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("url", urls);
            bundle.putStringArrayList("name", names);
            switch (item.getItemId()) {
                case R.id.navigation_closet:
                    closetFragment.setArguments(bundle);
                    getSupportActionBar().setTitle(R.string.title_closet);
                    loadFragment(closetFragment);
                    return true;
                case R.id.navigation_outfit:
                    outfitFragment.setArguments(bundle);
                    getSupportActionBar().setTitle(R.string.title_outfit);
                    loadFragment(outfitFragment);
                    return true;
                case R.id.navigation_settings:
                    getSupportActionBar().setTitle(R.string.title_settings);
                    loadFragment(settingsFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);

        uploads = new ArrayList<>();
        urls = new ArrayList<>();
        names = new ArrayList<>();

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                progressDialog.dismiss();
                uploads.clear();

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }

                Collections.sort(uploads, new Comparator<Upload>() {
                    @Override
                    public int compare(final Upload object1, final Upload object2) {
                        return object1.getName().compareTo(object2.getName());
                    }
                });

                for (Upload upload : uploads) {
                    urls.add(upload.getUrl());
                    names.add(upload.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        closetFragment = new ClosetFragment();
        outfitFragment = new OutfitFragment();
        settingsFragment = new SettingsFragment();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("url", urls);
        switch (navigation.getSelectedItemId()) {
            case R.id.navigation_closet:
                closetFragment.setArguments(bundle);
                loadFragment(closetFragment);
                getSupportActionBar().setTitle(R.string.title_closet);
                break;
            case R.id.navigation_outfit:
                outfitFragment.setArguments(bundle);
                loadFragment(outfitFragment);
                getSupportActionBar().setTitle(R.string.title_outfit);
                break;
            case R.id.navigation_settings:
                settingsFragment.setArguments(bundle);
                loadFragment(settingsFragment);
                getSupportActionBar().setTitle(R.string.title_settings);
                break;
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ButterKnife.bind(this);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
