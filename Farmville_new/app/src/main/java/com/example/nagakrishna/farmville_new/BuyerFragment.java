package com.example.nagakrishna.farmville_new;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuyerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuyerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyerFragment extends Fragment {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ArrayList<SellerDetails> returnValues = new ArrayList<SellerDetails>();
    List<SellerDetails> itemlist = new ArrayList();
//    ArrayList<String> listItems = new ArrayList<String>();
    public ListView listView;

    public BuyerFragment() {
        // Required empty public constructor
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buyer, container, false);
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, 0);
        String email = prefs.getString("email", null);
        GetMongoAsyncTask task = new GetMongoAsyncTask();
        try {
            returnValues = task.execute(email).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(SellerDetails x: returnValues){
            itemlist.add(x);
        }

        listView = (ListView) view.findViewById(R.id.listBuyerItems);
        listView.setAdapter(new BuyerCustomListAdapter(getActivity().getBaseContext(), itemlist));

        return view;
    }

    public class BuyerCustomListAdapter extends BaseAdapter {

        Context context;
        Bitmap bitmap;
        private List<SellerDetails> sellerDetails;
        LayoutInflater layoutInflater;
        boolean isImageFitToScreen;

        public BuyerCustomListAdapter(Context context, List<SellerDetails> sellerDetails) {
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

            final ListContent holder;
            if (this.layoutInflater == null) {
                this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null) {
                holder = new ListContent();
                convertView = this.layoutInflater.inflate(R.layout.listview_buyeritems, null);
                holder.product = (TextView) convertView.findViewById(R.id.listProductBuyer);
                holder.quanity = (TextView) convertView.findViewById(R.id.listQuantityBuyer);
                holder.desciption = (TextView) convertView.findViewById(R.id.listDescriptionBuyer);
                holder.imageView = (ImageView) convertView.findViewById(R.id.listImageViewBuyer);
                convertView.setTag(holder);
            }
            else{
                holder = (ListContent) convertView.getTag();
            }

            SellerDetails sd =  this.sellerDetails.get(position);
            holder.product.setText(sd.getProduct());
            holder.quanity.setText(sd.getQuantity());
            holder.desciption.setText(sd.getDescription());
            if(sd.getImageEncoderValue()!=null){
                holder.imageView.setImageBitmap(sd.getImageEncoderValue());
            }
            else {
//                holder.imageView.setImageBitmap(getResources().getDrawable(R.drawable.image_not_available));
                holder.imageView.setImageDrawable(getResources().getDrawable(R.drawable.image_not_available));
            }

            holder.imageView.setOnClickListener(mOnTitleClickListener_image);
            holder.product.setOnClickListener(detailsListener);
            holder.quanity.setOnClickListener(detailsListener);
            holder.desciption.setOnClickListener(detailsListener);


            return convertView;
        }

         class ListContent {

            TextView product;
            TextView quanity;
            TextView desciption;
            ImageView imageView;
        }
//
        public View.OnClickListener mOnTitleClickListener_image = new View.OnClickListener() {
            public void onClick(View v) {

                final int position = listView.getPositionForView((View) v.getParent());
                ImageView i = new ImageView(v.getContext());
                SellerDetails sd =  sellerDetails.get(position);
                i.setImageBitmap(sd.getImageEncoderValue());
                Toast toastView = new Toast(v.getContext());
                toastView.setView(i);
                toastView.setDuration(Toast.LENGTH_SHORT);
                toastView.setGravity(Gravity.FILL, 0, 0);
                toastView.show();

            }
        };

        public View.OnClickListener detailsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = listView.getPositionForView((View) v.getParent());
                SellerDetails sd = sellerDetails.get(position);
                Intent intent = new Intent(v.getContext(), SellersDetails.class);
                intent.putExtra("SellerEmail", sd.getEmail());
                startActivity(intent);
            }
        };
    }

}

