package tech.babako.passget;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class PaymentActivity extends AppCompatActivity {

    String basketID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        basketID = getIntent().getStringExtra(Intent.EXTRA_TEXT);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Ödeme Sayfası");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
