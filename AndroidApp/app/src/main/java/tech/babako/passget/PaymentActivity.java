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

import tech.babako.passget.Utils.NetworkUtils;

public class PaymentActivity extends AppCompatActivity {

    String basketID;
    ProgressBar pb_payment;
    ConstraintLayout cl_payment_card_info, cl_payment_success;
    Button button_payment_submit;
    TextView tv_payment_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        basketID = getIntent().getStringExtra(Intent.EXTRA_TEXT);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Ödeme Sayfası");

        initializeViews();

        button_payment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DoPaymentBackground().execute("sync",basketID);
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
            String sync_url = "http://dijkstra.ug.bcc.bilkent.edu.tr/~arif.terzioglu/GE401/cleanBasket.php";

            if (type.equals("sync")){
                return NetworkUtils.postToDatabase(basketID,sync_url);
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
}
