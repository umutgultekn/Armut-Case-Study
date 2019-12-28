package com.ugultekin.casestudy.views.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ugultekin.casestudy.R;

import java.net.MalformedURLException;
import java.net.URL;

public class KarakterDetayActivity extends AppCompatActivity {

    private static final String TAG = "KarakterDetayActivity";

    public static final int KARAKTER_DETAY_REQUEST = 1;

    public static final String EXTRA_IMAGE =
            "com.ugultekin.casestudy.EXTRA_IMAGE";
    public static final String EXTRA_NAME =
            "com.ugultekin.casestudy.EXTRA_NAME";
    public static final String EXTRA_PLAYERCLASS =
            "com.ugultekin.casestudy.EXTRA_PLAYERCLASS";
    public static final String EXTRA_RARITY =
            "com.ugultekin.casestudy.EXTRA_RARITY";
    public static final String EXTRA_TYPE =
            "com.ugultekin.casestudy.EXTRA_TYPE";
    public static final String EXTRA_COST =
            "com.ugultekin.casestudy.EXTRA_COST";
    public static final String EXTRA_ATTACK =
            "com.ugultekin.casestudy.EXTRA_ATTACK";
    public static final String EXTRA_HEALTH =
            "com.ugultekin.casestudy.EXTRA_HEALTH";
    public static final String EXTRA_TEXT =
            "com.ugultekin.casestudy.EXTRA_TEXT";
    public static final String EXTRA_FLAVOR =
            "com.ugultekin.casestudy.EXTRA_FLAVOR";


    private ImageView karakter_img_view;
    private TextView karakter_player_class, karakter_rarity, karakter_type,
            karakter_cost, karakter_attack, karakter_health, karakter_text, karakter_flavor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karakter_detay);


        karakter_img_view = (ImageView) findViewById(R.id.img_view_karakter);
        karakter_player_class = (TextView) findViewById(R.id.karakter_detay_player_class);
        karakter_rarity = (TextView) findViewById(R.id.karakter_detay_rarity);
        karakter_type = (TextView) findViewById(R.id.karakter_detay_type);
        karakter_cost = (TextView) findViewById(R.id.karakter_detay_cost);
        karakter_attack = (TextView) findViewById(R.id.karakter_detay_attack);
        karakter_health = (TextView) findViewById(R.id.karakter_detay_health);
        karakter_text = (TextView) findViewById(R.id.karakter_detay_text);
        karakter_flavor = (TextView) findViewById(R.id.karakter_detay_flavor);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_NAME)) {

            setTitle(intent.getStringExtra(EXTRA_NAME));

            // if image has not found show hearhstone.png
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .placeholder(R.drawable.hearthstone)
                    .error(R.drawable.hearthstone);

            try {
                URL url_image = new URL(intent.getStringExtra(EXTRA_IMAGE));

                Glide.with(this)
                        .load(url_image)
                        .apply(options)
                        .into(karakter_img_view);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            String content = intent.getStringExtra(EXTRA_TEXT);

            if (content != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    karakter_text.setText(Html.fromHtml(replaceText(content), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    karakter_text.setText(Html.fromHtml(replaceText(content)));
                }
            } else {
                Log.d(TAG, "check_content: Content Null!");
            }

            karakter_player_class.setText(intent.getStringExtra(EXTRA_PLAYERCLASS));
            karakter_rarity.setText(intent.getStringExtra(EXTRA_RARITY));
            karakter_type.setText(intent.getStringExtra(EXTRA_TYPE));
            karakter_cost.setText(intent.getStringExtra(EXTRA_COST));
            karakter_attack.setText(intent.getStringExtra(EXTRA_ATTACK));
            karakter_health.setText(intent.getStringExtra(EXTRA_HEALTH));
//            karakter_text.setText(intent.getStringExtra(EXTRA_TEXT));
            karakter_flavor.setText(intent.getStringExtra(EXTRA_FLAVOR));
        }

    }

    public String replaceText(String text) {

        if (text != null) {
            return text.replace("\\n", "<br>");

        } else {
            return "";
        }
    }

    public String replaceUmutToGultekin(String text) {

        if (text != null) {
            return text.replace("umut", "gültekin");

        } else {
            return "";
        }
    }

    public String replaceTextBlank(String text) {

        if (text != null) {
            return text.replace("_", " ");

        } else {
            return "";
        }
    }

}

