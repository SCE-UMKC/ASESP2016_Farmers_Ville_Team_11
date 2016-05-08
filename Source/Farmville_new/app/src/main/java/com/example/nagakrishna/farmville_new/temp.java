package com.example.nagakrishna.farmville_new;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class temp extends ListActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ArrayList<SellerDetails> returnValues = new ArrayList<SellerDetails>();
    List<SellerDetails> itemlist = new ArrayList();
    public ListView listView;
    SellerPostsCustomListAdapter adapter;
    public int selectedIndex = -1;
    private TextView btnDelete, btnEdit, emptyList;
    private LinearLayout ly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_temp);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        }


//        try {
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, 0);
            String email = prefs.getString("email", null);
//            returnValues = (ArrayList<SellerDetails>) getIntent().getSerializableExtra("FILES_TO_SEND");

//            GetSellerPost task = new GetSellerPost(getBaseContext());
//            returnValues = task.execute(email).get();

//        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }

        btnEdit = (TextView)findViewById(R.id.btnEdit);
        btnDelete = (TextView)findViewById(R.id.btnDelete);
        ly = (LinearLayout)findViewById(R.id.lySellerPosts);
//        emptyList = (TextView)findViewById(R.id.empty);


        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        new GetSellerPost(new SellerPostsListener() {
            @Override
            public void servicesuccess(ArrayList<SellerDetails> sellerDetailses) {
                progressDialog.dismiss();
                returnValues = sellerDetailses;
                if(returnValues.size()>0){
//                    btnEdit.setVisibility(View.VISIBLE);
//                    btnDelete.setVisibility(View.VISIBLE);
                    ly.setVisibility(View.VISIBLE);
                }
                else {
//                    emptyList.setVisibility(View.VISIBLE);
                }
                for(SellerDetails x: returnValues){
                    itemlist.add(x);
                }
                adapter = new SellerPostsCustomListAdapter(getBaseContext(), itemlist);
                setListAdapter(adapter);
            }
        },this).execute(email);

    }

    public void BtnEditPost(View v){
        if(selectedIndex == -1){
            Toast.makeText(temp.this, "Select Post to Edit", Toast.LENGTH_SHORT).show();
        }
        else{
            int value = selectedIndex;
            SellerDetails editPost =  itemlist.get(value);
            Intent intent = new Intent(this, EditPosts.class);
            intent.putExtra("created", editPost.created);
            intent.putExtra("product", editPost.product);
            intent.putExtra("quantity", editPost.quantity);
            intent.putExtra("description",editPost.description);
            intent.putExtra("image",editPost.imageEncoderValue);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, NavigationActvity.class);
        startActivity(setIntent);
    }

    public void BtnDeletePost(View v){
        if(selectedIndex == -1){
            Toast.makeText(temp.this, "Select Post to Delete", Toast.LENGTH_SHORT).show();
        }
        else{
            int value = selectedIndex;
            SellerDetails editPost =  itemlist.get(value);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("created", editPost.created);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            DeletePost deletePost = new DeletePost(getBaseContext());
            //deletePost.execute(editPost.created);
            deletePost.execute(String.valueOf(jsonObject));
            Intent refresh = new Intent(this, temp.class);
            startActivity(refresh);//Start the same Activity
            finish();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        adapter.setSelectedIndex(position);
        adapter.notifyDataSetChanged();
    }

    class SellerPostsCustomListAdapter extends BaseAdapter {

        private List<SellerDetails> sellerDetails;
        LayoutInflater layoutInflater;
        Context context;


        public void setSelectedIndex(int index){
            selectedIndex = index;
        }

        public SellerPostsCustomListAdapter(Context context, List<SellerDetails> sellerDetails) {
            this.context = context;
            this.sellerDetails = sellerDetails;
            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return this.sellerDetails.size();
        }

        @Override
        public Object getItem(int position) {
            return this.sellerDetails.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (this.layoutInflater == null) {
                this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null) {
                convertView = this.layoutInflater.inflate(R.layout.listview_sellerposts, null);
            }

            TextView product = (TextView) convertView.findViewById(R.id.listProductSellerPost);
            TextView quantity = (TextView) convertView.findViewById(R.id.listQuantitySellerPost);
            TextView description = (TextView) convertView.findViewById(R.id.listDescriptionSellerPost);
            ImageView image = (ImageView) convertView.findViewById(R.id.listImageViewSellerPost);
            SellerDetails sd = this.sellerDetails.get(position);
            product.setText(sd.getProduct());
            quantity.setText(sd.getQuantity());
            description.setText(sd.getDescription());
            if(sd.getImageEncoderValue()!=null){
                image.setImageBitmap(sd.getImageEncoderValue());
            }
            else {
                image.setImageDrawable(convertView.getResources().getDrawable(R.drawable.image_not_available));
            }

            RadioButton rbSelect = (RadioButton) convertView
                    .findViewById(R.id.radionBtnSellerPosts);
            if(selectedIndex == position){
                rbSelect.setChecked(true);
            }
            else{
                rbSelect.setChecked(false);
            }
            return  convertView;
        }

        public View.OnClickListener editListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final int position = listView.getPositionForView((View) v.getParent());
//                SellerDetails sd = sellerDetails.get(position);
//                Intent intent = new Intent(v.getContext(), SellersDetails.class);
//                intent.putExtra("SellerEmail", sd.getEmail());
//                startActivity(intent);
            }
        };

    }

}



class GetSellerPost extends AsyncTask<String, String, ArrayList<SellerDetails>> {
   Context context;
    SellerPostsListener listener;
   public GetSellerPost(SellerPostsListener listener, Context context){
       this.context = context;
       this.listener = listener;
   }

    @Override
    protected void onPostExecute(ArrayList<SellerDetails> sellerDetailses) {
        super.onPostExecute(sellerDetailses);
        listener.servicesuccess(sellerDetailses);
    }

    String temp_output, server_output;
    @Override
    protected ArrayList<SellerDetails> doInBackground(String... params) {
        ArrayList<SellerDetails> SellerPosts = new ArrayList<SellerDetails>();
        try
        {
            String email = params[0];
            //String urlNew = "http://farmville.kwkpfawsu2.us-west-2.elasticbeanstalk.com/sellerDetails/SellerDetails?email=" + email;
            String urlNew = context.getResources().getString(R.string.IndividualSellerPosts) + email;

            URL url = new URL(urlNew);
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            while ((temp_output = br.readLine()) != null) {
                server_output = temp_output;
            }

            // create a basic db list
            String mongoarray = "{ artificial_basicdb_list: "+server_output+"}";
            Object o = com.mongodb.util.JSON.parse(mongoarray);


            DBObject dbObj = (DBObject) o;
            BasicDBList posts = (BasicDBList) dbObj.get("artificial_basicdb_list");

            for (Object obj : posts) {
                DBObject userObj = (DBObject) obj;

                SellerDetails temp = new SellerDetails();
                temp.setDoc_id(userObj.get("_id").toString());
                temp.setProduct(userObj.get("Product").toString());
                temp.setQuantity(userObj.get("Quantity").toString());
                temp.setDescription(userObj.get("Description").toString());
                temp.setImageEncoderValue(userObj.get("Image").toString());
                temp.setEmail(userObj.get("email").toString());
                temp.setCreated(userObj.get("created").toString());
                SellerPosts.add(temp);

            }

        }catch (Exception e) {
            e.getMessage();
        }

        return SellerPosts;
    }
}

class DeletePost extends AsyncTask<String, String, String>{

    Context context;
    public DeletePost(Context context){
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {

        //String deleteItem = params[0];
        String JsonDATA = params[0];
//        String urlNew = context.getResources().getString(R.string.deletePost) + deleteItem;
        String urlNew = context.getResources().getString(R.string.deletePost);
        try {
            URL url = new URL(urlNew);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(JsonDATA);
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
//            if (conn.getResponseCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + conn.getResponseCode());
//            }
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    (conn.getInputStream())));

//            while ((temp_output = br.readLine()) != null) {
//                server_output = temp_output;
//            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

