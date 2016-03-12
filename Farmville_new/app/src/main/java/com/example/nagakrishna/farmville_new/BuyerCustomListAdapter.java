package com.example.nagakrishna.farmville_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Naga Krishna on 11-03-2016.
 */
public class BuyerCustomListAdapter extends BaseAdapter {

    Context context;
    private List<SellerDetails> sellerDetails;
    LayoutInflater layoutInflater;

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

        if (this.layoutInflater == null) {
            this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.listview_buyeritems, null);
        }
        TextView product = (TextView) convertView.findViewById(R.id.listProductBuyer);
        TextView quanity = (TextView) convertView.findViewById(R.id.listQuantityBuyer);
        TextView desciption = (TextView) convertView.findViewById(R.id.listDescriptionBuyer);

        SellerDetails sd =  this.sellerDetails.get(position);
        product.setText(sd.getProduct());
        quanity.setText(sd.getQuantity());
        desciption.setText(sd.getDescription());
        return convertView;
    }
}
