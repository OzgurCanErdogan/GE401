package tech.babako.passget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebase_auth;
    FirebaseUser firebase_user;
    FirebaseDatabase firebase_db;
    DatabaseReference db_ref;

    TextView tv_profile_name;
    TextView tv_profile_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseApp.initializeApp(this);
        firebase_db = FirebaseDatabase.getInstance();
        firebase_auth = FirebaseAuth.getInstance();
        firebase_user = firebase_auth.getCurrentUser();

        tv_profile_name = findViewById(R.id.tv_profile_name);
        tv_profile_email = findViewById(R.id.tv_profile_email);

        db_ref = firebase_db.getReference("users");
        Query query = db_ref.orderByChild("email").equalTo(firebase_user.getEmail());
        query.addValueEventListener(valueEventListener);
    }

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                tv_profile_name.setText(snapshot.child("name").getValue(String.class) + " " + snapshot.child("surname").getValue(String.class));
                tv_profile_email.setText(snapshot.child("email").getValue(String.class));
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
