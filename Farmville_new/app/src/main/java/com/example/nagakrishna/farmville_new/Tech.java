package com.example.nagakrishna.farmville_new;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import java.net.HttpURLConnection;

public class Tech extends AppCompatActivity {

    // private TextView tvData;
    private ListView lvTrends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        }
        lvTrends = (ListView)findViewById(R.id.lvTrends);
        new JSONTask().execute("https://api.nal.usda.gov/pubag/rest/search/?query=publication_year:2016&api_key=gzwAFCUsY9iCe02CBxAYj7d71kVQ0ZFavkmsd6jG");
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

    public class JSONTask extends AsyncTask<String,String,List<TrendsModel>> {
        @Override
        protected List<TrendsModel> doInBackground(String... params) { HttpURLConnection connection=null;
            BufferedReader reader=null;
            try {
                URL url=new URL(params[0]);
                connection= (HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream stream= connection.getInputStream();
                reader= new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer=new StringBuffer();
                String line="" ;
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }
                String finalJson=buffer.toString();
                JSONObject parentObject=new JSONObject(finalJson);
                JSONArray parentArray=parentObject.getJSONArray("resultList");
                List<TrendsModel> trendsModelList=new ArrayList<>();
                for(int i=0;i<parentArray.length();i++){
                    List<String> auth1 = new ArrayList<String>();
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    TrendsModel trendsModel=new TrendsModel();
                    trendsModel.setTitle(finalObject.getString("title"));
                    trendsModel.setSource(finalObject.getString("source"));
                    JSONArray jsonArray = finalObject.getJSONArray("authors");
                    StringBuilder sb = new StringBuilder();
                    String temp;
                    for(int k=0;k<jsonArray.length();k++){
//                       king.add(jsonArray.get(k).toString());
                        temp = jsonArray.getString(k);
                        sb.append(temp);
                        sb.append(", ");
                    }
                    trendsModel.setAuthorsList(sb.toString());
                    trendsModelList.add(trendsModel);
                }
                return trendsModelList;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {

                if(connection!=null) {
                    connection.disconnect();
                }
                try {
                    if(reader!=null) {

                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(List<TrendsModel> result){

            super.onPostExecute(result);

            TrendsAdapter adapter=new TrendsAdapter(getApplicationContext(),R.layout.row1,result);
            lvTrends.setAdapter(adapter);
        }

    }
    public class TrendsAdapter extends ArrayAdapter{
        private List<TrendsModel> trendsModelList;
        private int resource;
        private LayoutInflater inflater;

        public TrendsAdapter(Context context, int resource, List<TrendsModel> objects) {
            super(context, resource, objects);
            trendsModelList=objects;
            this.resource=resource;
            inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=inflater.inflate(resource,null);

            }

            TextView tvTitle;
            TextView tvAuthors;
            TextView tvSource;

            tvTitle=(TextView)convertView.findViewById(R.id.tvTitle);
            tvAuthors=(TextView)convertView.findViewById(R.id.tvAuthors);
            tvSource=(TextView)convertView.findViewById(R.id.tvSource);


            tvTitle.setText(trendsModelList.get(position).getTitle());



            tvAuthors.setText(trendsModelList.get(position).getAuthorsList());
            tvSource.setText(trendsModelList.get(position).getSource());
            return convertView;
        }
    }
}
