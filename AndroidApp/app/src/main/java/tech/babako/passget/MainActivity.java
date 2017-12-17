package tech.babako.passget;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;


public class MainActivity extends AppCompatActivity {

    ImageButton imgButton_identifyPassGet;
    ConstraintLayout cl_PassGetID;
    EditText et_passGetID;
    Button button_passGetID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgButton_identifyPassGet = findViewById(R.id.imgbutton_identify_passget);
        cl_PassGetID = findViewById(R.id.cl_passgetID);
        et_passGetID = findViewById(R.id.et_passgetID);
        button_passGetID = findViewById(R.id.button_passgetID);

        imgButton_identifyPassGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgButton_identifyPassGet.setVisibility(View.GONE);
                cl_PassGetID.setVisibility(View.VISIBLE);
            }
        });

        button_passGetID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passGetID = et_passGetID.getText().toString();
                Intent intent = new Intent(MainActivity.this,ShoppingCartActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT,passGetID);
                startActivity(intent);
            }
        });


    }
}
