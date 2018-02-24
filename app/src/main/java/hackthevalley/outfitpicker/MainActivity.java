package hackthevalley.outfitpicker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    ClosetFragment closetFragment;
    OutfitFragment outfitFragment;
    SettingsFragment settingsFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_closet:
                    getSupportActionBar().setTitle(R.string.title_closet);
                    loadFragment(closetFragment);
                    return true;
                case R.id.navigation_outfit:
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

        closetFragment = new ClosetFragment();
        outfitFragment = new OutfitFragment();
        settingsFragment = new SettingsFragment();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        switch(navigation.getSelectedItemId()) {
            case R.id.navigation_closet:
                loadFragment(closetFragment);
                getSupportActionBar().setTitle(R.string.title_closet);
                break;
            case R.id.navigation_outfit:
                loadFragment(outfitFragment);
                getSupportActionBar().setTitle(R.string.title_outfit);
                break;
            case R.id.navigation_settings:
                loadFragment(settingsFragment);
                getSupportActionBar().setTitle(R.string.title_settings);
                break;
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
