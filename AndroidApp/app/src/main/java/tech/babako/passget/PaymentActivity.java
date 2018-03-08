package tech.babako.passget;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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

import tech.babako.passget.Utils.NetworkUtils;

public class PaymentActivity extends AppCompatActivity {

    String basketID;
    ProgressBar pb_payment;
    ConstraintLayout cl_payment_card_info, cl_payment_success;
    Button button_payment_submit;
    TextView tv_payment_info;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference db_ref;
    String user_id;
    String shopping_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final String[] shoppingInfo = getIntent().getStringArrayExtra(Intent.EXTRA_TEXT);
        final String basketID = shoppingInfo[0];
 //       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Ödeme Sayfası");
        initializeViews();

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db_ref = firebaseDatabase.getReference("users");

        Query query = db_ref.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(valueEventListener);

        button_payment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopping_id = db_ref.child(user_id+"/shoppings").push().getKey();
                for (int i = 1; i<4; i++){
                    if (shoppingInfo[i] != null) {
                        db_ref.child(user_id+"/shoppings/"+shopping_id).child(shoppingInfo[i]).setValue(1);
                    }
                }
                new DoPaymentBackground().execute("sync",basketID,shopping_id);
            }
        });

    }

    void initializeViews(){
        pb_payment = findViewById(R.id.pb_payment);
        cl_payment_card_info = findViewById(R.id.cl_payment_card_info);
        cl_payment_success = findViewById(R.id.cl_payment_success);
        button_payment_submit = findViewById(R.id.button_payment_submit);
        tv_payment_info = findViewById(R.id.tv_payment_info);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    class DoPaymentBackground extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb_payment.setVisibility(View.VISIBLE);
            cl_payment_card_info.setVisibility(View.INVISIBLE);
            tv_payment_info.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String type = strings[0];
            basketID = strings[1];
            //Log.d("Shopping",basketID);

            if (type.equals("sync")){
                return NetworkUtils.endShopping(basketID,shopping_id);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pb_payment.setVisibility(View.INVISIBLE);
            cl_payment_success.setVisibility(View.VISIBLE);
        }
    }

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                user_id = snapshot.getKey();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
