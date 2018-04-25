package tech.babako.passget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import tech.babako.passget.Adapters.HistoryDetailAdapter;

public class HistoryDetail extends AppCompatActivity {

    TextView tv_history_detail_name, tv_history_detail_cost, tv_history_detail_date;
    RecyclerView rv_history_detail;
    HistoryDetailAdapter historyDetailAdapter;
    String detail_name, detail_cost, detail_date;
    String[] names, ids, costs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        Intent intent = getIntent();
        String json = intent.getStringExtra(Intent.EXTRA_TEXT);

        tv_history_detail_name = findViewById(R.id.tv_history_detail_id_value);
        tv_history_detail_cost = findViewById(R.id.tv_history_detail_cost_value);
        tv_history_detail_date = findViewById(R.id.tv_history_detail_date_value);

        rv_history_detail = findViewById(R.id.rv_history_detail);
        rv_history_detail.setHasFixedSize(false);

        historyDetailAdapter = new HistoryDetailAdapter();
        rv_history_detail.setAdapter(historyDetailAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv_history_detail.setLayoutManager(llm);

        try {
            JSONObject jsonObject = new JSONObject(json);
            detail_name = jsonObject.getString("store");
            detail_date = jsonObject.getString("date");
            detail_cost = jsonObject.getString("total");

            tv_history_detail_date.setText(detail_date);
            tv_history_detail_cost.setText(detail_cost + "₺");
            tv_history_detail_name.setText(detail_name);

            JSONArray items = jsonObject.getJSONArray("items");
            names = new String[items.length()];
            ids = new String[items.length()];
            costs = new String[items.length()];

            for (int i = 0; i<items.length(); i++) {
                names[i] = items.getJSONObject(i).getString("name");
                ids[i] = items.getJSONObject(i).getString("rfid");
                costs[i] = items.getJSONObject(i).getString("price");
            }
            historyDetailAdapter.setData(names,costs, ids);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
