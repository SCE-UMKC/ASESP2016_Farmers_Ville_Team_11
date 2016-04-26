package com.example.nagakrishna.farmville_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Account extends AppCompatActivity {


    private Button changePassword, changeEmail, changePic, changeName, changeAddress, changeNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        changeEmail = (Button)findViewById(R.id.btnChangeEmail);
        changeEmail.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getBaseContext(), ChangeEmail.class));
                    }
                }
        );

        changePassword = (Button)findViewById(R.id.btnChangePassword);
        changePassword.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getBaseContext(), ChangePassword.class));
                    }
                }
        );

        changeNumber = (Button)findViewById(R.id.btnChangeNumber);
        changeNumber.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getBaseContext(), ChangeNumber.class));
                    }
                }
        );

        changeName = (Button)findViewById(R.id.btnChangeName);
        changeName.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getBaseContext(), ChangeName.class));
                    }
                }
        );

        changeAddress = (Button)findViewById(R.id.btnChangeAddress);
        changeAddress.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getBaseContext(), ChangeAddress.class));
                    }
                }
        );

        changePic = (Button)findViewById(R.id.btnChangePic);
        changePic.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getBaseContext(), ChangeImage.class));
                    }
                }
        );

    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, NavigationActvity.class);
        startActivity(setIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
