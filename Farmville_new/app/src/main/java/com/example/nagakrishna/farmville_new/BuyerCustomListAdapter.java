package com.example.nagakrishna.farmville_new;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Naga Krishna on 11-03-2016.
 */
//public class BuyerCustomListAdapter extends BaseAdapter {
//
//    Context context;
//    Bitmap bitmap;
//    private List<SellerDetails> sellerDetails;
//    LayoutInflater layoutInflater;
//    boolean isImageFitToScreen;
//
//    public BuyerCustomListAdapter(Context context, List<SellerDetails> sellerDetails) {
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
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        final ListContent holder;
//        if (this.layoutInflater == null) {
//            this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//        if (convertView == null) {
//            holder = new ListContent();
//            convertView = this.layoutInflater.inflate(R.layout.listview_buyeritems, null);
//            holder.product = (TextView) convertView.findViewById(R.id.listProductBuyer);
//            holder.quanity = (TextView) convertView.findViewById(R.id.listQuantityBuyer);
//            holder.desciption = (TextView) convertView.findViewById(R.id.listDescriptionBuyer);
//            holder.imageView = (ImageView) convertView.findViewById(R.id.listImageViewBuyer);
//            convertView.setTag(holder);
//        }
//        else{
//            holder = (ListContent) convertView.getTag();
//        }
//
//        SellerDetails sd =  this.sellerDetails.get(position);
//        holder.product.setText(sd.getProduct());
//        holder.quanity.setText(sd.getQuantity());
//        holder.desciption.setText(sd.getDescription());
//        holder.imageView.setImageBitmap(sd.getImageEncoderValue());
//
//        holder.imageView.setOnClickListener(mOnTitleClickListener_image);
//
////        imageView.setOnClickListener(
////                new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        if(isImageFitToScreen) {
////                            isImageFitToScreen=false;
////                            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
////                            imageView.setAdjustViewBounds(true);
////                        }else{
////                            isImageFitToScreen=true;
//////                            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
////                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
////                        }
////                    }
////                }
////        );
//        return convertView;
//    }
//
//    static class ListContent {
//
//        TextView product;
//        TextView quanity;
//        TextView desciption;
//        ImageView imageView;
//    }
//
//    public View.OnClickListener mOnTitleClickListener_image = new View.OnClickListener() {
//        public void onClick(View v) {
//
//            final int position = sellerDetails.getPositionForView((View) v.getParent());
//
//            ImageView i = new ImageView(v.getContext());
//
//            SellerDetails sd =  this.sellerDetails.get(position);
//            i.setImageBitmap();
//
//            Toast toastView = new Toast(v.getContext());
//            toastView.setView(i);
//            toastView.setDuration(Toast.LENGTH_LONG);
//            toastView.setGravity(Gravity.CENTER, 0, 0);
//            toastView.show();
//
//            //Log.d("you are click on image view", "you are click on image view");
//
//        }
//    };
//}
