package tech.babako.passget;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tech.babako.passget.Fragments.FeedFragment;
import tech.babako.passget.Fragments.OffersFragment;


public class MainActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar my_toolbar;
    private FirebaseAuth firebase_auth;
    private FirebaseUser firebase_user;
    static final int LOGIN_SIGNUP_REQ = 31;
    private BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        my_toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        checkUser();

        bottom_nav = findViewById(R.id.main_nav_bottom);
        bottom_nav.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        updateNavigationUI();

        FirebaseApp.initializeApp(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (requestCode == LOGIN_SIGNUP_REQ){
           if (resultCode == RESULT_OK) {
               Toast.makeText(this, "Welcome", Toast.LENGTH_LONG);
               firebase_user = firebase_auth.getCurrentUser();
           }
       }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_qr_code:
                Intent intent = new Intent(MainActivity.this,QRCodeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                FirebaseAuth.getInstance().signOut();
                checkUser();
                return true;
            case R.id.action_account:
                startActivity(new Intent(this,ProfileActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()){
                case R.id.nav_feed:
                    my_toolbar.setTitle("PassGet | Anasayfa");
                    fragment = new FeedFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_offers:
                    my_toolbar.setTitle("PassGet | Kampanyalar");
                    fragment = new OffersFragment();
                    loadFragment(fragment);
                    return true;
                default:
                    return true;
            }
        }
    };

    private void updateNavigationUI() {
        int nav_item = bottom_nav.getSelectedItemId();
        Fragment fragment;
        switch (nav_item){
            case R.id.nav_feed:
                my_toolbar.setTitle("PassGet | Anasayfa");
                fragment = new FeedFragment();
                loadFragment(fragment);
                return;
            case R.id.nav_offers:
                my_toolbar.setTitle("PassGet | Kampanyalar");
                fragment = new OffersFragment();
                loadFragment(fragment);
                return;
            default:
                return;
        }
    }

    private void checkUser() {
        firebase_auth = FirebaseAuth.getInstance();
        firebase_user = firebase_auth.getCurrentUser();
        if (firebase_user == null){
            // Navigate to login if there is no user login
            startActivityForResult(new Intent(this,LoginActivity.class),LOGIN_SIGNUP_REQ);
        } else {
            // If there is a user update UI
        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
