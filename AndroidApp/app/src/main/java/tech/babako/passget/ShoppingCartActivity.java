package tech.babako.passget;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import tech.babako.passget.Utils.NetworkUtils;

public class ShoppingCartActivity extends AppCompatActivity {

    String basketID;
    TextView tv_name_item_1, tv_name_item_2, tv_name_item_3, tv_property_item_1,
            tv_property_item_2, tv_property_item_3, tv_stock_no_item_1, tv_stock_no_item_2
            , tv_stock_no_item_3, tv_price_item_1, tv_price_item_2, tv_price_item_3,
            tv_total_cost_value, tv_kdv_value, tv_total_price_value;
    ImageView img_item_1, img_item_2, img_item_3;
    CardView cv_item_1, cv_item_2, cv_item_3;
    Button button_submit_shopping_cart;
    ProgressBar pb_shopping_cart;
    SwipeRefreshLayout srl_shopping_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        basketID = intent.getStringExtra(Intent.EXTRA_TEXT);

        setTitle("Alışveriş Sepetiniz: " + basketID);

        initializeViews();

        new GetShoppingCart().execute("sync",basketID);

        srl_shopping_cart.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetShoppingCart().execute("sync",basketID);
            }
        });

        button_submit_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartActivity.this,PaymentActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT,basketID);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    void initializeViews(){
        tv_name_item_1 = findViewById(R.id.tv_name_item_1);
        tv_name_item_2 = findViewById(R.id.tv_name_item_2);
        tv_name_item_3 = findViewById(R.id.tv_name_item_3);

        tv_property_item_1 = findViewById(R.id.tv_property_item_1);
        tv_property_item_2 = findViewById(R.id.tv_property_item_2);
        tv_property_item_3 = findViewById(R.id.tv_property_item_3);

        tv_stock_no_item_1 = findViewById(R.id.tv_stock_no_item_1);
        tv_stock_no_item_2 = findViewById(R.id.tv_stock_no_item_2);
        tv_stock_no_item_3 = findViewById(R.id.tv_stock_no_item_3);

        tv_price_item_1 = findViewById(R.id.tv_price_item_1);
        tv_price_item_2 = findViewById(R.id.tv_price_item_2);
        tv_price_item_3 = findViewById(R.id.tv_price_item_3);

        tv_total_cost_value = findViewById(R.id.tv_total_cost_value);
        tv_kdv_value = findViewById(R.id.tv_kdv_value);
        tv_total_price_value = findViewById(R.id.tv_total_price_value);

        img_item_1 = findViewById(R.id.img_item_1);
        img_item_2 = findViewById(R.id.img_item_2);
        img_item_3 = findViewById(R.id.img_item_3);

        cv_item_1 = findViewById(R.id.cv_item_1);
        cv_item_2 = findViewById(R.id.cv_item_2);
        cv_item_3 = findViewById(R.id.cv_item_3);

        button_submit_shopping_cart = findViewById(R.id.button_submit_shopping_cart);
        pb_shopping_cart = findViewById(R.id.pb_shopping_cart);
        srl_shopping_cart = findViewById(R.id.srl_shopping_cart);
    }

    class GetShoppingCart extends AsyncTask<String,Void,String> {

        private String response = null;
        private String basketID;
        private String[] productIDs;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb_shopping_cart.setVisibility(View.VISIBLE);
            cv_item_1.setVisibility(View.INVISIBLE);
            cv_item_2.setVisibility(View.INVISIBLE);
            cv_item_3.setVisibility(View.INVISIBLE);

            tv_total_cost_value.setVisibility(View.INVISIBLE);
            tv_kdv_value.setVisibility(View.INVISIBLE);
            tv_total_price_value.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String type = strings[0];
            basketID = strings[1];
            //Log.d("Shopping",basketID);
            String sync_url = "http://dijkstra.ug.bcc.bilkent.edu.tr/~arif.terzioglu/GE401/basket.php";
            String sync_url2 = "http://dijkstra.ug.bcc.bilkent.edu.tr/~arif.terzioglu/GE401/list.php";

            if(type.equals("sync"))
            {
                NetworkUtils.postToDatabase(basketID,sync_url);
                return NetworkUtils.postToDatabase(basketID,sync_url2);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            TextView[] item_names = {tv_name_item_1,tv_name_item_2,tv_name_item_3};
            if(s != null) {
                productIDs = s.split("\n");
                Log.d("LENGTH: ", "" + productIDs.length);
                for (int i = 0; i < 3; i++) {
                    item_names[i].setText(productIDs[i]);
                }
                pb_shopping_cart.setVisibility(View.INVISIBLE);
                cv_item_1.setVisibility(View.VISIBLE);
                cv_item_2.setVisibility(View.VISIBLE);
                cv_item_3.setVisibility(View.VISIBLE);
                tv_total_cost_value.setVisibility(View.VISIBLE);
                tv_kdv_value.setVisibility(View.VISIBLE);
                tv_total_price_value.setVisibility(View.VISIBLE);
                srl_shopping_cart.setRefreshing(false);
            } else{
                Log.d("SHOPPING", "OLMADI");
            }
        }
    }

}


