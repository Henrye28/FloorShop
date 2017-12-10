package com.skymall.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.skymall.GlobalFunctions;
import com.skymall.R;
import com.skymall.pages.NearbyMapPage;

public class NearbyStoreInfoFragment extends Fragment {
    View view;
    SimpleDraweeView fragmentImageview;
    TextView fragmentTextTitle;
    TextView fragmentTextAddress;
    ImageView fragmentGo;
    String latlng;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nearby_store_info_fragment, container, false);
        fragmentImageview = (SimpleDraweeView)view.findViewById(R.id.storepic);
        fragmentTextTitle = (TextView)view.findViewById(R.id.storeTitle);
        fragmentTextAddress = (TextView)view.findViewById(R.id.storelocation);
        fragmentGo = (ImageView)view.findViewById(R.id.gotodes);

        String[] result = ((NearbyMapPage) getActivity()).getResult();

        fragmentImageview.setImageURI(Uri.parse(result[0]));
        fragmentTextAddress.setText(result[1]);
        fragmentTextTitle.setText(result[2]);
        latlng = result[3];

        fragmentGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AAAAAAADDRESS", latlng);
                GlobalFunctions.pickNavApp(latlng, fragmentTextAddress.getText().toString(),getActivity());
            }
        });
        return  view;
    }


}