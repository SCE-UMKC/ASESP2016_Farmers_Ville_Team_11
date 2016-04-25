package com.example.nagakrishna.farmville_new;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Posts extends  AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ArrayList<SellerDetails> returnValues = new ArrayList<SellerDetails>();
    List<SellerDetails> itemlist = new ArrayList();
    public ListView listView;
    //SellerPostsCustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        try {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, 0);
        String email = prefs.getString("email", null);

        GetSellerPost task = new GetSellerPost(getBaseContext());

            returnValues = task.execute(email).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        for(SellerDetails x: returnValues){
            itemlist.add(x);
        }

        listView = (ListView) findViewById(R.id.listSellerPosts);
        //adapter = new SellerPostsCustomListAdapter(this, itemlist);
        //listView.setAdapter(adapter);

    }

//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//        adapter.setSelectedIndex(position);
//        adapter.notifyDataSetChanged();
//    }

}

//class SellerPostsCustomListAdapter extends BaseAdapter{
//
//    private List<SellerDetails> sellerDetails;
//    LayoutInflater layoutInflater;
//    Context context;
//    int selectedIndex = -1;
//
//    public void setSelectedIndex(int index){
//        selectedIndex = index;
//    }
//
//    public SellerPostsCustomListAdapter(Context context, List<SellerDetails> sellerDetails) {
//        this.context = context;
//        this.sellerDetails = sellerDetails;
//        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//    @Override
//    public int getCount() {
//        return this.sellerDetails.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return this.sellerDetails.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (this.layoutInflater == null) {
//            this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//        if (convertView == null) {
//            convertView = this.layoutInflater.inflate(R.layout.listview_sellerposts, null);
//        }
//
//        TextView product = (TextView) convertView.findViewById(R.id.listProductSellerPost);
//        TextView quantity = (TextView) convertView.findViewById(R.id.listQuantitySellerPost);
//        TextView description = (TextView) convertView.findViewById(R.id.listDescriptionSellerPost);
//        ImageView image = (ImageView) convertView.findViewById(R.id.listImageViewSellerPost);
//        SellerDetails sd = this.sellerDetails.get(position);
//        product.setText(sd.getProduct());
//        quantity.setText(sd.getQuantity());
//        description.setText(sd.getDescription());
//        if(sd.getImageEncoderValue()!=null){
//            image.setImageBitmap(sd.getImageEncoderValue());
//        }
//        else {
//            image.setImageDrawable(convertView.getResources().getDrawable(R.drawable.image_not_available));
//        }
//
//        RadioButton rbSelect = (RadioButton) convertView
//                .findViewById(R.id.radionBtnSellerPosts);
//        if(selectedIndex == position){
//            rbSelect.setChecked(true);
//        }
//        else{
//            rbSelect.setChecked(false);
//        }
//        return  convertView;
//    }
//}
//
//class GetSellerPost extends AsyncTask<String, String, ArrayList<SellerDetails>>{
//    @Override
//    protected void onPostExecute(ArrayList<SellerDetails> sellerDetailses) {
//        super.onPostExecute(sellerDetailses);
//    }
//
//    String temp_output, server_output;
//    @Override
//    protected ArrayList<SellerDetails> doInBackground(String... params) {
//        ArrayList<SellerDetails> SellerPosts = new ArrayList<SellerDetails>();
//        try
//        {
//            String email = params[0];
//            String urlNew = "http://farmville.kwkpfawsu2.us-west-2.elasticbeanstalk.com/sellerDetails/SellerDetails?email=" + email;
//            URL url = new URL(urlNew);
//            HttpURLConnection conn = (HttpURLConnection) url
//                    .openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Accept", "application/json");
//
//            if (conn.getResponseCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + conn.getResponseCode());
//            }
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    (conn.getInputStream())));
//
//            while ((temp_output = br.readLine()) != null) {
//                server_output = temp_output;
//            }
//
//            // create a basic db list
//            String mongoarray = "{ artificial_basicdb_list: "+server_output+"}";
//            Object o = com.mongodb.util.JSON.parse(mongoarray);
//
//
//            DBObject dbObj = (DBObject) o;
//            BasicDBList posts = (BasicDBList) dbObj.get("artificial_basicdb_list");
//
//            for (Object obj : posts) {
//                DBObject userObj = (DBObject) obj;
//
//                SellerDetails temp = new SellerDetails();
//                temp.setDoc_id(userObj.get("_id").toString());
//                temp.setProduct(userObj.get("Product").toString());
//                temp.setQuantity(userObj.get("Quantity").toString());
//                temp.setDescription(userObj.get("Description").toString());
//                temp.setImageEncoderValue(userObj.get("Image").toString());
//                temp.setEmail(userObj.get("email").toString());
//                SellerPosts.add(temp);
//
//            }
//
//        }catch (Exception e) {
//            e.getMessage();
//        }
//
//        return SellerPosts;
//    }
//}
