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

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import tech.babako.passget.Utils.NetworkUtils;

public class ShoppingCartActivity extends AppCompatActivity {

    String basketID;
    TextView tv_name_item_1, tv_name_item_2, tv_name_item_3, tv_property_item_1,
            tv_property_item_2, tv_property_item_3, tv_stock_no_item_1, tv_stock_no_item_2, tv_stock_no_item_3, tv_price_item_1, tv_price_item_2, tv_price_item_3,
            tv_total_cost_value, tv_kdv_value, tv_total_price_value;
    ImageView img_item_1, img_item_2, img_item_3;
    CardView cv_item_1, cv_item_2, cv_item_3;
    Button button_submit_shopping_cart;
    ProgressBar pb_shopping_cart;
    SwipeRefreshLayout srl_shopping_cart;
    FirebaseAuth firebase_auth;
    FirebaseUser firebase_user;
    FirebaseDatabase firebase_db;
    DatabaseReference db_ref;
    String user_id;
    String[] shop = new String[4];

    DecimalFormat format = new DecimalFormat("##.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        basketID = intent.getStringExtra(Intent.EXTRA_TEXT);

        shop[0] = basketID;

        setTitle("Alışveriş Sepetiniz: " + basketID);
        initializeViews();

        FirebaseApp.initializeApp(this);
        firebase_db = FirebaseDatabase.getInstance();
        firebase_auth = FirebaseAuth.getInstance();
        firebase_user = firebase_auth.getCurrentUser();

        db_ref = firebase_db.getReference("users");
        Query query = db_ref.orderByChild("email").equalTo(firebase_user.getEmail());
        query.addValueEventListener(valueEventListener);

        srl_shopping_cart.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetShoppingCart().execute("sync", basketID, user_id);
            }
        });

        button_submit_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartActivity.this, PaymentActivity.class);

                /*
                    Extra structure: 0: basket_id, 1 2 3 .... n : Product ids
                 */
                intent.putExtra(Intent.EXTRA_TEXT, shop);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    void initializeViews() {
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

        pb_shopping_cart.setVisibility(View.VISIBLE);
        cv_item_1.setVisibility(View.GONE);
        cv_item_2.setVisibility(View.GONE);
        cv_item_3.setVisibility(View.GONE);

        tv_total_cost_value.setVisibility(View.INVISIBLE);
        tv_kdv_value.setVisibility(View.INVISIBLE);
        tv_total_price_value.setVisibility(View.INVISIBLE);
    }

    class GetShoppingCart extends AsyncTask<String, Void, String> {

        private String response = null;
        private String basketID;
        private String[] productIDs;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pb_shopping_cart.setVisibility(View.VISIBLE);
            cv_item_1.setVisibility(View.GONE);
            cv_item_2.setVisibility(View.GONE);
            cv_item_3.setVisibility(View.GONE);

            tv_total_cost_value.setVisibility(View.INVISIBLE);
            tv_kdv_value.setVisibility(View.INVISIBLE);
            tv_total_price_value.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String type = strings[0];
            basketID = strings[1];
            user_id = strings[2];
            //Log.d("Shopping",basketID);
            String sync_url2 = "http://dijkstra.ug.bcc.bilkent.edu.tr/~arif.terzioglu/GE401/list.php";

            if (type.equals("sync")) {
                NetworkUtils.syncWithBasket(basketID, user_id);
                return NetworkUtils.postToDatabase(basketID, sync_url2);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            TextView[] item_names = {tv_name_item_1, tv_name_item_2, tv_name_item_3};
            TextView[] item_sizes = {tv_property_item_1, tv_property_item_2, tv_property_item_3};
            TextView[] item_prices = {tv_price_item_1, tv_price_item_2, tv_price_item_3};
            TextView[] item_stock_nos = {tv_stock_no_item_1, tv_stock_no_item_2, tv_stock_no_item_3};
            ImageView[] item_images = {img_item_1, img_item_2, img_item_3};

            CardView[] items = {cv_item_1, cv_item_2, cv_item_3};


            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonItems = jsonObject.getJSONArray("items");
                    int itemCount = jsonItems.length();
                    String itemName;
                    String itemPrice;
                    String itemSize;
                    String itemStockNo;
                    double totalCostTaxFree = 0;
                    double totalTax = 0;
                    double totalCost = 0;
                    int kot = R.drawable.kot;
                    int tshirt = R.drawable.c3647e63;
                    int hirka = R.drawable.ae9efd29;

                    for (int i = 0; i < itemCount; i++) {
                        itemName = jsonItems.getJSONObject(i).getString("name");
                        itemPrice = jsonItems.getJSONObject(i).getString("price");
                        itemSize = jsonItems.getJSONObject(i).getString("size");
                        itemStockNo = jsonItems.getJSONObject(i).getString("id");

                        shop[i+1] = itemStockNo;

                        item_names[i].setText(itemName);
                        item_sizes[i].setText("Beden: " + itemSize);
                        item_prices[i].setText(itemPrice + "₺");
                        item_stock_nos[i].setText("Stok No: " + String.valueOf(itemStockNo).toUpperCase());
                        items[i].setVisibility(View.VISIBLE);
                        if (itemName.equals(("Martin Kot Pantolon"))) {
                            item_images[i].setImageDrawable(getDrawable(kot));
                        } else if (itemName.equals("V Yaka T-Shirt")) {
                            item_images[i].setImageDrawable(getDrawable(tshirt));
                        } else
                            item_images[i].setImageDrawable(getDrawable(hirka));

                        totalCostTaxFree += Double.parseDouble(itemPrice);
                        totalTax += (Double.parseDouble(itemPrice) * 0.18);
                    }

                    if (itemCount < 3) {
                        int i = 0;
                        while ((itemCount + i) != 3) {
                            items[itemCount + i].setVisibility(View.GONE);
                            i++;
                        }
                    }

                    totalCost = totalCostTaxFree + totalTax;

                    tv_total_cost_value.setText(String.valueOf(format.format(totalCostTaxFree)));
                    tv_kdv_value.setText(String.valueOf(format.format(totalTax)));
                    tv_total_price_value.setText(String.valueOf(format.format(totalCost)));

                    pb_shopping_cart.setVisibility(View.INVISIBLE);
                    tv_total_cost_value.setVisibility(View.VISIBLE);
                    tv_kdv_value.setVisibility(View.VISIBLE);
                    tv_total_price_value.setVisibility(View.VISIBLE);
                    srl_shopping_cart.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("SHOPPING", "OLMADI");
            }
        }
    }

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                user_id = snapshot.getKey();
            }
            new GetShoppingCart().execute("sync", basketID, user_id);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}


