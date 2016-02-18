package com.example.nagakrishna.farmersville;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SellerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SellerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellerFragment extends Fragment {

    private Spinner spinnerUnits;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seller, container, false);
        spinnerUnits = (Spinner)view.findViewById(R.id.units);
        String values [] = {"Kgs", "Pounds", "Gallons"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, values );
        spinnerUnits.setAdapter(arrayAdapter);

//        spinnerUnits.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    }
//                }
//        );

        return view;

    }




}
