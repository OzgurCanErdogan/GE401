package tech.babako.passget.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;

import java.util.ArrayList;

import tech.babako.passget.Adapters.HistoryAdapter;
import tech.babako.passget.HistoryDetail;
import tech.babako.passget.MainActivity;
import tech.babako.passget.R;
import tech.babako.passget.Utils.JSONParser;
import tech.babako.passget.Utils.NetworkUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements HistoryAdapter.HistoryItemClickListener{

    RecyclerView rv_history;
    LinearLayoutManager lm_history;
    HistoryAdapter historyAdapter;

    FirebaseAuth firebase_auth;
    FirebaseUser firebase_user;
    FirebaseDatabase firebase_db;
    DatabaseReference db_ref;
    ArrayList<String> shopping_ids;
    String[] dates = null;
    String[] costs = null;

    String user_id;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getContext();
        shopping_ids = new ArrayList<>();
        FirebaseApp.initializeApp(context);
        firebase_db = FirebaseDatabase.getInstance();
        firebase_auth = FirebaseAuth.getInstance();
        firebase_user = firebase_auth.getCurrentUser();

        db_ref = firebase_db.getReference("users");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Query query = db_ref.orderByChild("email").equalTo(firebase_user.getEmail());
        query.addValueEventListener(valueEventListener);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        rv_history = view.findViewById(R.id.rv_history);
        rv_history.setHasFixedSize(true);

        lm_history = new LinearLayoutManager(getContext());
        rv_history.setLayoutManager(lm_history);

        historyAdapter = new HistoryAdapter(this);
        rv_history.setAdapter(historyAdapter);

        return view;
    }

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                user_id = snapshot.getKey();
            }
            new GetUserShoppings().execute();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onHistoryClick(int position, String json) throws JSONException {
        Intent intent = new Intent(getActivity(), HistoryDetail.class);
        intent.putExtra(Intent.EXTRA_TEXT,JSONParser.getHistoryDetail(json,position));
        startActivity(intent);
    }


    class GetUserShoppings extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                return NetworkUtils.getUserHistory(user_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                Log.d("JSON", s);
                historyAdapter.setData(JSONParser.parseHistory((s)),s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
