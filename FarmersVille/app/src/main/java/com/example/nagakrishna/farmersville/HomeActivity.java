package com.example.nagakrishna.farmersville;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import static com.example.nagakrishna.farmersville.R.mipmap.*;

public class HomeActivity extends AppCompatActivity {

    private ImageView imageView1, imageView2, imageView3, imageView4,
            imageView5,imageView6, imageView7, imageView8, imageView9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView1 = (ImageView)findViewById(R.id.imageviewTAB1);
        imageView2 = (ImageView)findViewById(R.id.imageviewTAB2);
        imageView3 = (ImageView)findViewById(R.id.imageviewTAB3);
        imageView4 = (ImageView)findViewById(R.id.imageviewTAB4);
        imageView5 = (ImageView)findViewById(R.id.imageviewTAB5);
        imageView6 = (ImageView)findViewById(R.id.imageviewTAB6);
        imageView7 = (ImageView)findViewById(R.id.imageviewTAB7);
        imageView8 = (ImageView)findViewById(R.id.imageviewTAB8);
        imageView9 = (ImageView)findViewById(R.id.imageviewTAB9);
        imageView1.setImageDrawable(getResources().getDrawable(R.drawable.weather));
        imageView2.setImageDrawable(getResources().getDrawable(R.drawable.market));
        imageView3.setImageDrawable(getResources().getDrawable(R.drawable.news));
        imageView4.setImageDrawable(getResources().getDrawable(R.drawable.books));
        imageView5.setImageDrawable(getResources().getDrawable(R.drawable.market_prices));
        imageView6.setImageDrawable(getResources().getDrawable(R.drawable.suggestions));
        imageView7.setImageDrawable(getResources().getDrawable(R.drawable.places));
        imageView8.setImageDrawable(getResources().getDrawable(R.drawable.settings));
        imageView9.setImageDrawable(getResources().getDrawable(R.drawable.temp));


    }


    public void Weather(View v){

    }

    public void MarketTrade(View v){
        Intent marketTradeIntent = new Intent(this, MarketTrade.class);
        startActivity(marketTradeIntent);
    }

    public void News(View v){

    }

    public void Journal(View v){

    }

    public void MarketPrices(View v){

    }

    public void Suggestions(View v){

    }

    public void MarketPlaces(View v){

    }

    public void Settings(View v){

    }

}
