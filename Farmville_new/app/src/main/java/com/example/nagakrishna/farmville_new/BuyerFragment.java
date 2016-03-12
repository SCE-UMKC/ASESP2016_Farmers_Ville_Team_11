package com.example.nagakrishna.farmville_new;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

    ArrayList<SellerDetails> returnValues = new ArrayList<SellerDetails>();
    List<SellerDetails> itemlist = new ArrayList();
//    ArrayList<String> listItems = new ArrayList<String>();
    ListView listView;

    public BuyerFragment() {
        // Required empty public constructor
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buyer, container, false);
        GetMongoAsyncTask task = new GetMongoAsyncTask();
        try {
            returnValues = task.execute().get();
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
//        listView.setAdapter(new ArrayAdapter<String>(getActivity().getBaseContext(), R.layout.listview_buyeritems, listItems));
        return inflater.inflate(R.layout.fragment_buyer , container, false);
    }
}
