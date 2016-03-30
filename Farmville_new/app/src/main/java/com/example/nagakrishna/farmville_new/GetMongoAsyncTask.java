package com.example.nagakrishna.farmville_new;

import android.os.AsyncTask;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Naga Krishna on 11-03-2016.
 */
public class GetMongoAsyncTask extends AsyncTask<SellerDetails, Void, ArrayList<SellerDetails>> {
    static BasicDBObject user = null;
    static String OriginalObject = "";
    static String server_output = null;
    static String temp_output = null;

    @Override
    protected void onPostExecute(ArrayList<SellerDetails> sellerDetailses) {
        super.onPostExecute(sellerDetailses);


    }

    @Override
    protected ArrayList<SellerDetails> doInBackground(SellerDetails... arg0) {

        ArrayList<SellerDetails> mycontacts = new ArrayList<SellerDetails>();
        try
        {

            QueryBuilder qb = new QueryBuilder();
            URL url = new URL(qb.buildContactsGetURL());
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
            BasicDBList contacts = (BasicDBList) dbObj.get("artificial_basicdb_list");

            for (Object obj : contacts) {
                DBObject userObj = (DBObject) obj;

                SellerDetails temp = new SellerDetails();
                String k = userObj.get("_id").toString();
                temp.setDoc_id(userObj.get("_id").toString());
                temp.setProduct(userObj.get("Product").toString());
                temp.setQuantity(userObj.get("Quantity").toString());
                temp.setDescription(userObj.get("Description").toString());
                temp.setImageEncoderValue(userObj.get("Image").toString());
                mycontacts.add(temp);

            }

        }catch (Exception e) {
            e.getMessage();
        }

        return mycontacts;
    }
}
