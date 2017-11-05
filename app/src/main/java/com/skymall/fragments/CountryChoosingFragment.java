package com.skymall.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.skymall.R;
import com.skymall.adapters.CountryCodeListAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class CountryChoosingFragment extends Fragment {

    private View view;
    private ListView countryList;
    private ImageView back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.country_choosing_page, container, false);
        back = (ImageView)view.findViewById(R.id.back);
        countryList = (ListView)view.findViewById(R.id.countryList);
        Resources res = getResources();
        String[] cCode = res.getStringArray(R.array.country_code_list_en);
        ArrayList<String> countriesCodes = new ArrayList<String>(Arrays.asList(cCode));

        CountryCodeListAdapter adapter = new CountryCodeListAdapter(countriesCodes, getActivity());

       // countryList.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1,cCode));
        countryList.setAdapter(adapter);



        countryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //  RelativeLayout item = (RelativeLayout)parent.getItemAtPosition(position);
                String item = (String) parent.getItemAtPosition(position);
                TextView txtCountry = (TextView) getActivity().findViewById(R.id.txtCountry);
                TextView txtCountryCode = (TextView) getActivity().findViewById(R.id.txtCountryCode);
                String[] divided = item.split(" ");
                int len = divided.length;
                String codes = item.split(" ")[len - 1];
                String countries = item.replace(" " + codes, "");

                txtCountry.setText(countries);
                txtCountryCode.setText(codes);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_down_enter, R.anim.fragment_slide_up_exit);
                fragmentTransaction.remove(CountryChoosingFragment.this).commit();

            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_down_enter, R.anim.fragment_slide_up_exit);
                fragmentTransaction.remove(CountryChoosingFragment.this).commit();
            }
        });
        return  view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = android.app.Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
