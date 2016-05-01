package com.example.nagakrishna.farmville_new;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatUsers extends AppCompatActivity {

    private static final Firebase sRef = new Firebase("https://farmville.firebaseio.com/");
    private Firebase mFirebaseMessagesChat;
    private ChildEventListener mListenerUsers;
    private List<String> mUsersKeyList;
    private ChatListAdapter adapter;
    ListView listView;
    private String currentUsername, chatUsername;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setTitle("Conversations");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        }
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, 0);
        currentUsername = prefs.getString("name", null);

        listView = (ListView)findViewById(R.id.listviewChat);

        mUsersKeyList=new ArrayList<String>();
        queryFireChatUsers(getBaseContext());
    }

    private void queryFireChatUsers(final Context context) {

        mListenerUsers=sRef.child("Farmville_Chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()){
                    String userUid=dataSnapshot.getKey();
                    Log.d("userid", userUid);
                    String str = userUid.replace("-", "");
                    if(str.endsWith(currentUsername)){
                        str = str.replace(currentUsername, "");
                        mUsersKeyList.add(str);
                        listView.setAdapter(new ChatListAdapter(context, mUsersKeyList));
                    }
                    else if(str.startsWith(currentUsername)){
                        str = str.replace(currentUsername, "");
                        mUsersKeyList.add(str);
                        listView.setAdapter(new ChatListAdapter(context, mUsersKeyList));
                    }
                    Log.d("list", mUsersKeyList.toString());
                }
                else{
                    Log.d("temp", "else");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
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

class ChatListAdapter extends BaseAdapter{

    Context context;
    LayoutInflater layoutInflater;
    private List<String> name;

    public ChatListAdapter(Context context, List<String> name) {
        this.context = context;
        this.name = name;
        this.layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(layoutInflater == null){
            layoutInflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.listview_chat, null);
        final TextView txtName = (TextView)convertView.findViewById(R.id.txtViewChatName);
        txtName.setText(name.get(position));
        CircleImageView circleImageView = (CircleImageView)convertView.findViewById(R.id.userPhotoProfile);
        circleImageView.setImageDrawable(convertView.getResources().getDrawable(R.drawable.headshot_7));

        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Chat.class);
                intent.putExtra("chatName", txtName.getText().toString());
                v.getContext().startActivity(intent);
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Chat.class);
                intent.putExtra("chatName", txtName.getText().toString());
                v.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
