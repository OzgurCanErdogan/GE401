package tech.babako.passget.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bunya on 20-Dec-17.
 */

public class JSONParser {
    public static String[][] parseHistory(String jsonData) throws JSONException {
        /*
        0 - dates
        1 - costs
        2 - company
        3 -
         */
        String[][] history = new String[3][];
        JSONArray shoppingsArray = new JSONObject(jsonData).getJSONArray("shoppings");
        int count = shoppingsArray.length();

        String[] dates = new String[count];
        String[] costs = new String[count];
        String[] companies = new String[count];

        for (int i = 0; i < count; i++) {
            dates[i] = shoppingsArray.getJSONObject(i).getString("date");
            costs[i] = shoppingsArray.getJSONObject(i).getString("total");
            companies[i] = shoppingsArray.getJSONObject(i).getString("store");
        }
        history[0] = dates;
        history[1] = costs;
        history[2] = companies;

        return history;
    }

    public static String getHistoryDetail(String jsonData, int position) throws JSONException {
        JSONArray shoppingsArray = new JSONObject(jsonData).getJSONArray("shoppings");
        return shoppingsArray.getJSONObject(position).toString();
    }
}
