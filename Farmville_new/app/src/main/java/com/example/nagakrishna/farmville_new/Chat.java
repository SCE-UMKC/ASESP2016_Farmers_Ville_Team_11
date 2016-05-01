package com.example.nagakrishna.farmville_new;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Chat extends AppCompatActivity implements View.OnClickListener,
        MessageDataSource.MessagesCallbacks{

    public static final String USER_EXTRA = "USER";
    public static final String TAG = "ChatActivity";
    private ArrayList<Message> mMessages, msgs;
    private MessagesAdapter mAdapter;
    private String mRecipient;
    private ListView mListView;
    private Date mLastMessageDate = new Date();
    private String mConvoId, name, chatName;
    private MessageDataSource.MessagesListener mListener;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private String ReferenceUrl = "https://farmville.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle bundle = getIntent().getExtras();
        chatName = bundle.getString("chatName");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(chatName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        }
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, 0);
        name = prefs.getString("name", null);
        mRecipient = chatName;

        mListView = (ListView)findViewById(R.id.messages_list);
        mMessages = new ArrayList<>();
        msgs = new ArrayList<>();
        mAdapter = new MessagesAdapter(mMessages);
        mListView.setAdapter(mAdapter);




//        String a = name;
//        String b = chatName;
//        int compare = a.compareTo(b);
//        if(compare < 0){
//            String[] ids = {a,"-",b};
//        }
//        else{
//            String[] ids = {b,"-",a};
//        }
        setTitle(mRecipient);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageButton sendMessage = (ImageButton)findViewById(R.id.send_message);
        sendMessage.setOnClickListener(this);

        String[] ids = {name,"-",chatName};
        Arrays.sort(ids);
        mConvoId = ids[0]+ids[1]+ids[2];

        Firebase mFireChatUsersRef;


        mListener = MessageDataSource.addMessagesListener(mConvoId, this, name);

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
    public void onClick(View v) {
        EditText newMessageView = (EditText)findViewById(R.id.new_message);
        String newMessage = newMessageView.getText().toString();
        newMessageView.setText("");
        Message msg = new Message();
        msg.setDate(new Date());
        msg.setText(newMessage);
        msg.setSender(name);

//        mListView.setSelection(mMessages.size() - 4);
        MessageDataSource.saveMessage(msg, mConvoId);
    }

    @Override
    public void onMessageAdded(Message message) {
        mMessages.add(message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessageDataSource.stop(mListener);
    }


    private class MessagesAdapter extends ArrayAdapter<Message> {
//        @Override
//        public void notifyDataSetChanged() {
//            super.notifyDataSetChanged();
//
//            Intent intent = new Intent();
//            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
//            Notification noti = new Notification.Builder(getBaseContext())
//                    .setTicker("New Message")
//                    .setContentTitle("Title")
////                    .setContentText(message.getText().toString())
//                    .setContentIntent(pendingIntent).getNotification();
//            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            nm.notify(0, noti);
//
//        }

        MessagesAdapter(ArrayList<Message> messages){
            super(Chat.this, R.layout.message, R.id.message, messages);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            Message message = getItem(position);

            TextView nameView = (TextView)convertView.findViewById(R.id.message);
            nameView.setText(message.getText());

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)nameView.getLayoutParams();

            int sdk = Build.VERSION.SDK_INT;
            if (message.getSender().equals(name)){
                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    nameView.setBackground(getDrawable(R.drawable.bubble_right_green));
                } else{
                    nameView.setBackgroundDrawable(getDrawable(R.drawable.bubble_right_green));
                }
                layoutParams.gravity = Gravity.RIGHT;
            }else{
                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    nameView.setBackground(getDrawable(R.drawable.bubble_left_gray));
                } else{
                    nameView.setBackgroundDrawable(getDrawable(R.drawable.bubble_left_gray));
                }
                layoutParams.gravity = Gravity.LEFT;
            }

            nameView.setLayoutParams(layoutParams);


            return convertView;
        }
    }

}
