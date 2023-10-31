package stm.airble._5_add_airble.viewpager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import stm.airble.R;
import stm.airble._5_add_airble.AddAirbleActivity;

public class ViewHolderPage_2 extends Fragment {

    TextView add_airble_page_2_TextView_1, add_airble_page_2_TextView_2, add_airble_page_2_TextView_3;
    RecyclerView add_airble_page_2_RecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_add_airble_page_2, container, false);

        // 아이디 맞춰주기
        add_airble_page_2_TextView_1 = rootView.findViewById(R.id.add_airble_page_2_TextView_1);
        add_airble_page_2_TextView_2 = rootView.findViewById(R.id.add_airble_page_2_TextView_2);
        add_airble_page_2_TextView_3 = rootView.findViewById(R.id.add_airble_page_2_TextView_3);
        add_airble_page_2_RecyclerView = rootView.findViewById(R.id.add_airble_page_2_RecyclerView);

        ((AddAirbleActivity)AddAirbleActivity.add_airble_context).Page2_router_RecyclerView = add_airble_page_2_RecyclerView;

        ((AddAirbleActivity)AddAirbleActivity.add_airble_context).Page2_Setting();

        return rootView;
    }
}
/*
public class ViewHolderPage_2 extends Fragment {

    TextView add_airble_page_2_TextView_1, add_airble_page_2_TextView_2, add_airble_page_2_TextView_3;
    ViewPager2 add_airble_page_2_ViewPager2;
    AddAirble_Page_2_ViewPagerAdapter add_airble_page_2_Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_add_airble_page_2, container, false);

        // 아이디 맞춰주기
        add_airble_page_2_TextView_1 = rootView.findViewById(R.id.add_airble_page_2_TextView_1);
        add_airble_page_2_TextView_2 = rootView.findViewById(R.id.add_airble_page_2_TextView_2);
        add_airble_page_2_TextView_3 = rootView.findViewById(R.id.add_airble_page_2_TextView_3);
        ((AddAirbleActivity)AddAirbleActivity.add_airble_context).inner_ViewPager2 = rootView.findViewById(R.id.add_airble_page_2_ViewPager2);

        add_airble_page_2_Adapter = new AddAirble_Page_2_ViewPagerAdapter(getActivity());
        ((AddAirbleActivity)AddAirbleActivity.add_airble_context).inner_ViewPager2.setAdapter(add_airble_page_2_Adapter);
        ((AddAirbleActivity)AddAirbleActivity.add_airble_context).inner_ViewPager2.setUserInputEnabled(false);
        ((AddAirbleActivity)AddAirbleActivity.add_airble_context).inner_ViewPager2.setOffscreenPageLimit(2);


        return rootView;
    }
}
*/