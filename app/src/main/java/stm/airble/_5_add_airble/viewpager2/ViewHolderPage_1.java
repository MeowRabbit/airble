package stm.airble._5_add_airble.viewpager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import stm.airble.R;
import stm.airble._5_add_airble.AddAirbleActivity;

public class ViewHolderPage_1 extends Fragment {

    TextView add_airble_page_1_TextView_1, add_airble_page_1_TextView_2, add_airble_page_1_TextView_3, add_airble_page_1_TextView_4, add_airble_find_wifi_TextView;
    TextView shared_TextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_add_airble_page_1, container, false);

        // 아이디 맞춰주기
        add_airble_page_1_TextView_1 = rootView.findViewById(R.id.add_airble_page_1_TextView_1);
        add_airble_page_1_TextView_2 = rootView.findViewById(R.id.add_airble_page_1_TextView_2);
        add_airble_page_1_TextView_3 = rootView.findViewById(R.id.add_airble_page_1_TextView_3);
        add_airble_page_1_TextView_4 = rootView.findViewById(R.id.add_airble_page_1_TextView_4);
        add_airble_find_wifi_TextView = rootView.findViewById(R.id.add_airble_find_wifi_TextView);
        shared_TextView = rootView.findViewById(R.id.add_airble_shared_TextView);

        ((AddAirbleActivity)AddAirbleActivity.add_airble_context).Page1_connect_TextView = add_airble_page_1_TextView_4;
        ((AddAirbleActivity)AddAirbleActivity.add_airble_context).Page1_find_wifi_TextView = add_airble_find_wifi_TextView;
        ((AddAirbleActivity)AddAirbleActivity.add_airble_context).Page1_shared_TextView = shared_TextView;

        ((AddAirbleActivity)AddAirbleActivity.add_airble_context).Page1_Setting();

        return rootView;
    }
}