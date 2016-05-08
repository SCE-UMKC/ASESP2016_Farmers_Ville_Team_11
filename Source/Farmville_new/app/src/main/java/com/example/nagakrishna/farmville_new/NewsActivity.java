package com.example.nagakrishna.farmville_new;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.content.Context;
import android.os.AsyncTask;
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

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
public class NewsActivity extends AppCompatActivity {
    private TextView tvData;
    private ListView LvNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        }
        LvNews = (ListView) findViewById(R.id.lvNews);


        // Button btnHit=(Button)findViewById(R.id.btnhit);
        //tvData = (TextView)findViewById(R.id.tvJsonItem);


        //  new JSONTask().execute("https://gateway-a.watsonplatform.net/calls/data/GetNews?apikey=d7d533dd4390f989d2c18e28d8496756898e15a9&outputMode=json&start=now-1d&end=now&count=10&q.enriched.url.enrichedTitle.taxonomy.taxonomy_=%7Clabel=agriculture,score=%3E0.4%7C&return=enriched.url.url,enriched.url.title,enriched.url.text");




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new JSONTask().execute("https://gateway-a.watsonplatform.net/calls/data/GetNews?apikey=d7d533dd4390f989d2c18e28d8496756898e15a9&outputMode=json&start=now-1d&end=now&count=10&q.enriched.url.enrichedTitle.taxonomy.taxonomy_=%7Clabel=agriculture,score=%3E0.4%7C&return=enriched.url.url,enriched.url.title,enriched.url.text");

    }



    public class JSONTask extends AsyncTask<String,String,List<NewsModel> >{

        @Override
        protected List<NewsModel> doInBackground(String... params) {

            HttpURLConnection connection =null;
            BufferedReader reader=null;
            try {
                URL url=new URL(params[0]);
                connection=(HttpURLConnection)url.openConnection();
                connection.connect();

                InputStream stream=connection.getInputStream();

                reader=new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson=buffer.toString();
                JSONObject parentObject=new JSONObject(finalJson);
                JSONObject Result=parentObject.getJSONObject("result");
                JSONArray Docs=Result.getJSONArray("docs");

                //  StringBuffer finalBufferedData=new StringBuffer();
                List<NewsModel> newsModelList=new ArrayList<>();

                for(int i=0;i<Docs.length();i++) {

                    NewsModel newsModel=new NewsModel();
                    JSONObject finalObject = Docs.getJSONObject(i);
                    // String ID=finalObject.getString("id");
                    JSONObject Source = finalObject.getJSONObject("source");
                    JSONObject Enriched = Source.getJSONObject("enriched");
                    JSONObject Url = Enriched.getJSONObject("url");

//                    String TEXT = Url.getString("text");
//                    String TITLE = Url.getString("title");
//                    String LINK = Url.getString("url");
//
                    newsModel.setText(Url.getString("text"));
                    newsModel.setTitle(Url.getString("title"));
                    newsModel.setLink(Url.getString("url"));
                    //adding final object in the list
                    newsModelList.add(newsModel);
                    // finalBufferedData.append(TEXT+"\n"+TITLE+"\n"+LINK+"\n\n");

                }

                // return buffer.toString();
                return newsModelList;

            }


            catch (MalformedURLException e) {
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
                    if(reader!=null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<NewsModel> result) {
            super.onPostExecute(result);

            NewsAdapter adapter=new NewsAdapter(getApplicationContext(),R.layout.row,result);

            LvNews.setAdapter(adapter);

            // tvData.setText(result);
            //TODO need to set data to list
        }
    }


    public class NewsAdapter extends ArrayAdapter{

        private List<NewsModel> newsModelList;
        private int resource;
        private LayoutInflater inflater;
        public NewsAdapter(Context context, int resource, List<NewsModel> objects) {
            super(context, resource, objects);

            newsModelList=objects;
            this.resource=resource;
            inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null){

                convertView=inflater.inflate(resource,null);
            }

            TextView tvTitle;
            TextView tvText;
            final TextView tvLink;

            tvTitle=(TextView)convertView.findViewById(R.id.tvTitle);
            tvText=(TextView)convertView.findViewById(R.id.tvText);
            tvLink=(TextView)convertView.findViewById(R.id.tvLink);

            tvTitle.setText(newsModelList.get(position).getTitle());
            tvText.setText(newsModelList.get(position).getText());
            tvLink.setText(newsModelList.get(position).getLink());


            tvLink.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = tvLink.getText().toString();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    }
            );
            return convertView;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_refresh) {
//            new JSONTask().execute("https://gateway-a.watsonplatform.net/calls/data/GetNews?apikey=d7d533dd4390f989d2c18e28d8496756898e15a9&outputMode=json&start=now-1d&end=now&count=10&q.enriched.url.enrichedTitle.taxonomy.taxonomy_=%7Clabel=agriculture,score=%3E0.4%7C&return=enriched.url.url,enriched.url.title,enriched.url.text");
//
//
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}