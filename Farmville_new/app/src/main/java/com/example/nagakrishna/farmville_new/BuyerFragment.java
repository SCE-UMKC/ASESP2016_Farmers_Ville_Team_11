package com.example.nagakrishna.farmville_new;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuyerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuyerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyerFragment extends Fragment {

    public BuyerFragment() {
        // Required empty public constructor
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buyer , container, false);
    }
}
