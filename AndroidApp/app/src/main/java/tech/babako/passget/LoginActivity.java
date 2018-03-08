package tech.babako.passget;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import tech.babako.passget.Utils.NetworkUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText et_login_email, et_login_password ,et_signup_name, et_signup_surname, et_signup_email, et_signup_password;
    private Button button_login_submit, button_signup_submit;
    private TextView tv_login_signup, tv_signup_login, tv_signup_date;
    private ConstraintLayout cl_login, cl_signup;
    private Spinner spinner_gender;
    private ProgressBar pb_login;
    FirebaseAuth firebase_auth;
    FirebaseUser firebase_user;
    FirebaseDatabase firebase_database;
    static String user_id;
    long unix_time = 0;
    String text_time = "0";
    private String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);
        firebase_auth = FirebaseAuth.getInstance();
        firebase_database = FirebaseDatabase.getInstance();

        et_login_email = findViewById(R.id.et_login_email);
        et_login_password = findViewById(R.id.et_login_password);
        et_signup_email = findViewById(R.id.et_signup_email);
        et_signup_password = findViewById(R.id.et_signup_password);
        et_signup_name = findViewById(R.id.et_signup_name);
        et_signup_surname = findViewById(R.id.et_signup_surname);
        button_login_submit = findViewById(R.id.button_login_submit);
        button_signup_submit = findViewById(R.id.button_signup_submit);
        tv_signup_login = findViewById(R.id.tv_signup_login);
        tv_login_signup = findViewById(R.id.tv_login_signup);
        cl_login = findViewById(R.id.cl_login);
        cl_signup = findViewById(R.id.cl_signup);
        pb_login = findViewById(R.id.pb_login);
        pb_login.setVisibility(View.GONE);
        spinner_gender = findViewById(R.id.spinner_gender);
        tv_signup_date = findViewById(R.id.tv_signup_date);
        final Calendar c = Calendar.getInstance();

        tv_signup_date.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                final int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(LoginActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tv_signup_date.setText(i2 + "." + (i1+1) + "." + i);
                        Calendar cc = Calendar.getInstance();
                        c.set(i, i1, i2);
                        cc.set(i, i1, i2);
                        unix_time = cc.getTime().getTime();
                        text_time = i + "-" + (i1+1) + "-" + i2;
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_gender.setAdapter(adapter);

        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = adapterView.getItemAtPosition(i).toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                gender = "kadın";
            }
        });

        tv_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_login.setVisibility(View.GONE);
                cl_signup.setVisibility(View.VISIBLE);
            }
        });

        tv_signup_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_login.setVisibility(View.VISIBLE);
                cl_signup.setVisibility(View.GONE);
            }
        });


        button_login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_login.setVisibility(View.INVISIBLE);
                pb_login.setVisibility(View.VISIBLE);
                String email = et_login_email.getText().toString();
                String password = et_login_password.getText().toString();
                firebase_auth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // YAY
                                    Log.d("Login:","OK");
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    // Sign-in failed!
                                    Log.d("Login", "Failed");
                                    cl_login.setVisibility(View.VISIBLE);
                                    pb_login.setVisibility(View.INVISIBLE);
                                    Toast.makeText(LoginActivity.this,"Login failed, check your creditentials",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        button_signup_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_signup.setVisibility(View.INVISIBLE);
                pb_login.setVisibility(View.VISIBLE);
                final String name = et_signup_name.getText().toString();
                final String surname = et_signup_surname.getText().toString();
                final String email = et_signup_email.getText().toString();
                final String password = et_signup_password.getText().toString();
                final String user_gender = gender;

                firebase_auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = firebase_auth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this,name,Toast.LENGTH_LONG).show();
                                        Log.d("Profile update", "successful");
                                    }
                                }
                            });
                            String userKey = firebase_database.getReference("users").push().getKey();
                            user_id = userKey;

                            new PostUserToMysql().execute("sync",user_id, name,surname,gender,text_time);

                            firebase_database.getReference("users/" + userKey).child("name").setValue(removeSpaceEnd(name));
                            firebase_database.getReference("users/" + userKey).child("surname").setValue(removeSpaceEnd(surname));
                            firebase_database.getReference("users/" + userKey).child("email").setValue(removeSpaceEnd(email));
                            firebase_database.getReference("users/" + userKey).child("gender").setValue(removeSpaceEnd(user_gender));
                            firebase_database.getReference("users/" + userKey).child("birthday").setValue(unix_time);

                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Log.d("Signup", "Failed");
                            cl_signup.setVisibility(View.VISIBLE);
                            pb_login.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this,"Signup failed, check your creditentials",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    public String removeSpaceEnd(String str) {
        String result = "";
        if (str.charAt(str.length()-1) == ' '){
            result = str.substring(0,str.length()-1);
        } else
            result = str;
        return result;
    }

    class PostUserToMysql extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String type = strings[0];
            String uid = strings[1];
            String name = strings[2];
            String sname = strings[3];
            String gender = strings[4];
            String bday = strings[5];
            //Log.d("Shopping",basketID);
            String sync_url = "http://dijkstra.ug.bcc.bilkent.edu.tr/~arif.terzioglu/GE401/register.php";

            if (type.equals("sync")){
                return NetworkUtils.postUserToDatabase(uid,name,sname,gender,bday,sync_url);
            }
            return null;
        }
    }
}
