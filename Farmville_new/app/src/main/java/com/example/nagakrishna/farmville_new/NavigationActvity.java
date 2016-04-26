package com.example.nagakrishna.farmville_new;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NavigationActvity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private ImageView imageView7;
    private ImageView imageView8;
    private ImageView imageView9;
    private ImageView imageViewUser;
    private String email, image, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_actvity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.imageView1 = (ImageView) findViewById(R.id.imageviewTAB1);
        this.imageView2 = (ImageView) findViewById(R.id.imageviewTAB2);
        this.imageView3 = (ImageView) findViewById(R.id.imageviewTAB3);
        this.imageView4 = (ImageView) findViewById(R.id.imageviewTAB4);
        this.imageView5 = (ImageView) findViewById(R.id.imageviewTAB5);
        this.imageView6 = (ImageView) findViewById(R.id.imageviewTAB6);
        this.imageView7 = (ImageView) findViewById(R.id.imageviewTAB7);
        this.imageView8 = (ImageView) findViewById(R.id.imageviewTAB8);
        this.imageView9 = (ImageView) findViewById(R.id.imageviewTAB9);
        this.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.weather));
        this.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.market));
        this.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.news));
        this.imageView4.setImageDrawable(getResources().getDrawable(R.drawable.books));
        this.imageView5.setImageDrawable(getResources().getDrawable(R.drawable.market_prices));
        this.imageView6.setImageDrawable(getResources().getDrawable(R.drawable.suggestions));
        this.imageView7.setImageDrawable(getResources().getDrawable(R.drawable.places));
        this.imageView8.setImageDrawable(getResources().getDrawable(R.drawable.settings));
        this.imageView9.setImageDrawable(getResources().getDrawable(R.drawable.temp));




        View hView =  navigationView.getHeaderView(0);
        imageViewUser = (ImageView) hView.findViewById(R.id.navImageViewUser);
        TextView nav_user = (TextView)hView.findViewById(R.id.navTextViewUsername);
        TextView nav_email = (TextView)hView.findViewById(R.id.navTextViewEmail);

        //retrieving data from shared preference
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        email = prefs.getString("email", null);
        image = prefs.getString("image", null);
        name = prefs.getString("name", null);
        nav_user.setText(name);
        nav_email.setText(email);
        if(image.equals("null")){
//            imageViewUser.setImageDrawable(getResources().getDrawable(R.drawable.image_not_available));
        }
        else {
            Bitmap myBitmapAgain = decodeBase64(image);
            Bitmap roundImage = getRoundedShape(myBitmapAgain);
            imageViewUser.setImageBitmap(roundImage);
        }

    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_actvity, menu);
        return true;
    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 300;
        int targetHeight = 300;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);


        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, NavigationActvity.class));
        } else if (id == R.id.nav_posts) {
            startActivity(new Intent(this, temp.class));
        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_share) {
            Intent i=new Intent(android.content.Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Farmville Android Applicatoin");
            i.putExtra(android.content.Intent.EXTRA_TEXT, "https://github.com/nagakrishna/ASE-Project");
            startActivity(Intent.createChooser(i,"Share via"));
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Weather(View v) {
        startActivity(new Intent(this, Weather.class));
    }

    public void MarketTrade(View v) {
        startActivity(new Intent(this, MarketTrade.class));
    }

    public void News(View v) {
        startActivity(new Intent(this, NewsActivity.class));
    }

    public void Journal(View v) {
    }

    public void MarketPrices(View v) {
        startActivity(new Intent(this, MongoActivity.class));
    }

    public void Suggestions(View v) {
    }

    public void MarketPlaces(View v) {
        startActivity(new Intent(this, MarketLocations.class));
    }

    public void Settings(View v) {
//        final ProgressDialog progressDialog = new ProgressDialog(NavigationActvity.this,
//                R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();
//        ArrayList<SellerDetails> returnValues = new ArrayList<SellerDetails>();
//        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, 0);
//        String email = prefs.getString("email", null);
//        GetSellerPost task = new GetSellerPost(getBaseContext());
//        try {
//            returnValues = task.execute(email).get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        Intent intent = new Intent(this, temp.class);
//        intent.putParcelableArrayListExtra("FILES_TO_SEND", returnValues);
//        progressDialog.dismiss();
////        intent.putExtra("FILES_TO_SEND", returnValues);
//        startActivity(intent);
        startActivity(new Intent(this, temp.class));
    }

    public void Posts(View v){
        startActivity(new Intent(this, Account.class));
    }
}
