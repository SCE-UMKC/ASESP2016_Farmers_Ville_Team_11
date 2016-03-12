package com.example.nagakrishna.farmville_new;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.json.JSONObject;

public class MongoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mongo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Mongo();

    }

    public void Mongo(){
        MongoClientURI uri = new MongoClientURI("mongodb://naga:password@ds019648.mlab.com:19648/aselab7nag");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB(uri.getDatabase());
        DBCollection users = db.getCollection("ASELAB7");
        String data = "{\"city\":\"202022\"}";
        JSONObject params = null;
        try{
            params = new JSONObject(data);
            BasicDBObject user1 = new BasicDBObject();
            BasicDBObject query = new BasicDBObject();
            DBCursor docs = users.find(query);
            user1.put("naga", "krishna");
            user1.put("umkc", "vit");
            users.insert(user1);
        }
        catch (Exception e){

        }

//        for(Object key:params.keys() .toArray())
//        {
//            user1.put(key.toString(),params.get(key.toString()));
//        }

    }
}
