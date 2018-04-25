package tech.babako.passget;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class OfferDetail extends AppCompatActivity {

    TextView tv_title, tv_description;
    ImageView img_offer_detail;
    CollapsingToolbarLayout ctl;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra(Intent.EXTRA_TITLE);
        String detail = intent.getStringExtra(Intent.EXTRA_TEXT);
        int image = intent.getIntExtra("offer_image",R.drawable.mor_logo);
        ctl = findViewById(R.id.ctl_offer_detail);
        toolbar = findViewById(R.id.toolbar_offer_detail);
        ctl.setTitle(title);
        ctl.setExpandedTitleColor(getResources().getColor(R.color.transparent));
        ctl.setCollapsedTitleTextColor(getResources().getColor(R.color.primaryTextColor));


        img_offer_detail = findViewById(R.id.img_offer_detail);
        tv_description = findViewById(R.id.tv_offer_detail_description);
        tv_title = findViewById(R.id.tv_offer_detail_title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String imageTransitionName = intent.getStringExtra("TRANSITION");
            img_offer_detail.setTransitionName(imageTransitionName);
        }

        tv_title.setText(title);
        tv_description.setText(detail);
        img_offer_detail.setImageDrawable(getDrawable(image));

    }
}
