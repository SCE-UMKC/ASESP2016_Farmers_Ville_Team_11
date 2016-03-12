package com.example.nagakrishna.farmville_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Naga Krishna on 10-03-2016.
 */
public class VenueCustomListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    private List<MarketDataType> market;

    public VenueCustomListAdapter(Context context, List<MarketDataType> markets){
        this.context = context;
        this.market = markets;
        this.layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return market.size();
    }

    @Override
    public Object getItem(int position) {
        return market.get(position);
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
            convertView = layoutInflater.inflate(R.layout.listview_layout, null);
        TextView name = (TextView)convertView.findViewById(R.id.listname);
        TextView mrketid = (TextView)convertView.findViewById(R.id.listmarketid);
        TextView distance = (TextView)convertView.findViewById(R.id.listDistance);
        MarketDataType mar = market.get(position);
        name.setText(String.valueOf(mar.getName()));
        mrketid.setText(String.valueOf(mar.getMarketID()));
        distance.setText(String.valueOf(mar.getDistance()));
        return convertView;
    }
}
