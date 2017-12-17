package tech.babako.passget;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class ShoppingCartActivity extends AppCompatActivity {

    String basketID;
    TextView tv_name_item_1,tv_name_item_2,tv_name_item_3, tv_property_item_1
            ,tv_property_item_2, tv_property_item_3, tv_stock_no_item_1, tv_stock_no_item_2
            , tv_stock_no_item_3, tv_price_item_1, tv_price_item_2, tv_price_item_3
            ,tv_total_cost_value, tv_kdv_value, tv_total_price_value;
    ImageView img_item_1, img_item_2, img_item_3;
    CardView cv_item_1, cv_item_2, cv_item_3;
    Button button_submit_shopping_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        Intent intent = getIntent();
        basketID = intent.getStringExtra(Intent.EXTRA_TEXT);

        initilizeViews();

        button_submit_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartActivity.this,PaymentActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT,basketID);
                startActivity(intent);
            }
        });
    }

    void initilizeViews(){
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
    }
}


class GetShoppingCart extends AsyncTask<String,Void,String> {

    private String response = null;
    String basketID;

    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        basketID = strings[1];
        String sync_url = "http://dijkstra.ug.bcc.bilkent.edu.tr/~arif.terzioglu/GE401/basket.php";
        if(type.equals("sync"))
        {
            try
            {
                String basketID = strings[1];
                URL url = new URL(sync_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("basketID","UTF-8")+"="+URLEncoder.encode(basketID,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line;
                while((line = bufferedReader.readLine()) !=null)
                {
                    result +=line;
                }
                bufferedReader.close();
                outputStream.close();
                httpURLConnection.disconnect();
                response=result;
                return result;

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
